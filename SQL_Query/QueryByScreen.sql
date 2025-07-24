-- Màn hình 1 Slide 4: Tab Boards
-- 1. Query 4 suggested template theo Template Category
SELECT TOP 4 t.Title, t.BackgroundUrl
FROM Templates t
JOIN TemplateCategories tc ON tc.id=t.TemplateCategoryId
Where tc.Name='Geologist II' --':templateCategory'

-- 2. Query 4 Board truy cập gần đây của user
SELECT TOP 4 b.Name,b.BackgroundUrl
FROM Boards b
JOIN BoardUsers bu ON bu.BoardId=b.Id
JOIN Users u ON u.Id=bu.UserID
Where u.Id=2 --userId
ORDER BY bu.AccessedAt DESC

-- 3. Query tất cả Workspace mà User là Member
SELECT w.Name
FROM Workspaces w
JOIN Members m ON m.OwnerId=w.Id
JOIN OwnerTypes ot ON m.OwnerTypeId=ot.Id
WHERE ot.Value='Workspace' AND m.UserId=1

--4. Query tất cả Workspace mà User là Member. Trong mỗi workspace đó, lấy tất cả Board mà User cũng là Member
SELECT 
    w.Id AS WorkspaceId,
    w.Name AS WorkspaceName,
    b.Id AS BoardId,
    b.Name AS BoardName,
    b.Description,
    b.BackgroundUrl,
    b.CreatedAt
FROM Workspaces w
--Tìm Workspace mà User là Member
JOIN Members mw ON mw.OwnerId = w.Id
JOIN OwnerTypes otw ON otw.Id = mw.OwnerTypeId AND otw.Value = 'WORKSPACE'
--Tìm Board tương ứng với Workspace mà User cũng là Member
JOIN Boards b ON b.WorkspaceId = w.Id
JOIN Members mb ON mb.Id = b.Id
JOIN OwnerTypes otb ON otb.Id = mb.OwnerTypeId AND otb.Value = 'BOARD'
WHERE mw.UserId = 1
  AND mb.UserId = 1;



--5. Query tất cả closed boards mà user là member
SELECT b.Name, w.Name
FROM Boards b
JOIN Workspaces w ON w.Id=b.WorkspaceId
JOIN Members m ON m.OwnerId=b.Id
JOIN OwnerTypes ot ON m.OwnerTypeId=ot.Id
WHERE
    ot.Value='BOARD'
    AND m.UserId=3
    AND b.Status='CLOSED'

-- Màn hình 2 Templates tab Slide 5
-- 6. Lấy ra 10 template categories
SELECT TOP 10 
    Name,
    IconUrl
FROM TemplateCategories;

--7. Lấy ra New and notable templates
SELECT 
    t.Title,
    t.BackgroundUrl,
    t.CreatedAt,
    t.CreatedBy,
    t.Copied,
    t.Viewed,
    t.Description
FROM Templates t
ORDER BY 
    t.CreatedAt DESC, 
    t.Viewed DESC, 
    t.Copied DESC;

-- Màn hình 3 Template detail Slide 6
--8. Lấy ra template detail và Board đi theo template đó
SELECT t.Title, u.Username,t.Copied,t.Viewed,t.Description,b.Name,b.Status
FROM Templates t
JOIN Boards b ON b.id=t.BoardId
JOIN Users u ON t.CreatedBy=u.Id
WHERE t.Id=1 --templateId

-- Màn hình 4: Create Workspace Slide 7
--9. Insert data vào Workspaces
Insert INTO Workspaces (Name, Description,Type) VALUES ('','','')

-- Màn hình 5: Tab Boards in Workspace với userId = 1 Slide 8
--10. Lấy Workspace Name, SettingKey='visibility' và SettingValue liên quan đến SettingKeys của Workspace
with SettingValueForWorkspace AS
(
    select sv.OwnerId,sk.KeyName,so.Value
    from SettingValues sv
    JOIN SettingKeys sk ON sk.Id=sv.SettingKeyId
    JOIN SettingOptions so ON sv.Value=so.Id
    JOIN OwnerTypes ot ON ot.Id=sk.OwnerTypeId AND ot.Value='WORKSPACE'
    WHERE sk.KeyName='Visibility'
)
select w.Name,svfw.Value
from Workspaces w
JOIN Members m ON m.OwnerTypeId=w.Id
JOIN SettingValueForWorkspace svfw ON svfw.OwnerId=w.Id
WHERE w.Id=1

--11. Lấy 4 Board được đề xuất theo Template Category Type có status là Template
select TOP 4 b.Name,b.BackgroundUrl
from Boards b
JOIN Templates t ON t.BoardId=b.Id
JOIN TemplateCategories tc ON t.TemplateCategoryId=tc.Id
Where tc.Name='Operator'
ORDER BY t.Viewed desc, t.Copied desc

--12. Lấy ra mục Your boards: Lấy ra Board thuộc Workspace mà User cũng là Member của Board
select b.Name,b.BackgroundUrl
from Boards b
join  Members m On m.OwnerId=b.Id
join Workspaces w ON w.Id=b.WorkspaceId
join OwnerTypes ot ON ot.Id=m.OwnerTypeId AND ot.Value='BOARD'
WHERE w.Id=1 AND m.UserId=1

-- Màn hình 6: tab Member của Workspace Slide 10
--13. Lấy ra tất cả Member trong Workspace, số Board trong Workspace có Member đó tham gia và quyền trong Workspace tương ứng
with WorkspaceMembers as (
    select m.UserId, m.PermissionId
    from Members m
    join OwnerTypes ot on ot.id=m.OwnerTypeId
    where ot.Value='WORKSPACE' AND m.OwnerId=1
),
BoardInWorkspace as (
    select b.Id as BoardId
    from Boards b
    where b.WorkspaceId=1
),
BoardMembers as (
    select m.UserId,m.OwnerId as BoardId
    From Members m
    JOIN OwnerTypes ot on ot.Id=m.OwnerTypeId
    where ot.Value='BOARD'
)
select
    u.Username, u.LastActive,p.Name,
    COUNT(bm.BoardId) as NumBoardsJoined
from WorkspaceMembers wm
left join BoardMembers bm
    on bm.UserId=wm.UserId
left join BoardInWorkspace biw
    on bm.BoardId=biw.BoardId
join Users u on wm.UserId=u.Id
join Permissions p on wm.PermissionId=p.Id
group by wm.UserId, u.Username, u.LastActive, p.Name
--14. Lấy ra sharelink của workspace và status của sharelink
select sl.Token,sl.Status,p.Name
from ShareLinks sl
join Workspaces w on w.Id=sl.OwnerId
join Permissions p on sl.PermissionId=p.Id
where w.Id=1
--15. Update Status ShareLink của Workspace
update ShareLinks 
set Status=1
where 
    OwnerId=1
    and OwnerTypeId in (
        select Id
        from OwnerTypes
        where Value='WORKSPACE'
    )



