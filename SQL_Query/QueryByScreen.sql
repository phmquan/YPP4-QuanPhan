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

--Màn hình 7: Guest Tab trong Workspace Member
--16. Query Member của Board thuộc Workspace. Nhưng Member đó không thuộc Workspace
insert into Members(UserId,PermissionId,OwnerTypeId,OwnerId,InvitedBy,JoinedAt,Status) VALUES (1000,1,1,3,1,'','ACTIVE')
with WorkspaceBoardMembers as (
    select m.UserId,b.Id as BoardId,b.WorkspaceId
    from Members m 
    join Boards b on b.Id=m.OwnerId
    join OwnerTypes ot on ot.Id=m.OwnerTypeId AND ot.Value='BOARD'
    where b.WorkspaceId=3
),
WorkspaceMembers as(
    select m.UserId
    from Members m
    join Workspaces w on w.Id=m.OwnerId
    join OwnerTypes ot on ot.Id=m.OwnerTypeId AND ot.Value='WORKSPACE'
    where m.OwnerId=3
)
select u.Username,u.LastActive, COUNT(wbm.BoardId) as BoardMemberCount
from WorkspaceBoardMembers wbm
left join WorkspaceMembers wm on wbm.UserId=wm.UserId
join Users u on u.Id=wbm.UserId
where wm.UserId is null
group by u.Username,u.LastActive

--Màn hình 8: Share Board 
--17. Add Member vào Board với Permission
insert into Members(UserId, PermissionId,OwnerTypeId, OwnerId, InvitedBy, JoinedAt, Status) VALUES (1001,1,3,3,1,'','ACTIVE')
--18. Tạo ShareLink cho Board có Permission (Mỗi Board chỉ có 1 ShareLink)
if not exists(
    select 1 
    from ShareLinks sl 
    join OwnerTypes ot on ot.Id=sl.OwnerTypeId AND ot.Value='BOARD'
    where sl.OwnerId=1
)
begin insert into ShareLinks(OwnerTypeId,OwnerId,PermissionId,Token,Status) VALUES (3,1,1,'/path',1)
end
BEGIN
    PRINT 'Record already exists, skipping insert.'
END

--19. Update Status, Permission của ShareLink
UPDATE ShareLinks
SET 
    Status = 'ENABLED',        
    PermissionId = 2           
WHERE 
    OwnerId = 1
    AND OwnerTypeId IN (
        SELECT Id FROM OwnerTypes WHERE Value = 'BOARD'
    )
--20. Lấy ra tất cả Member của Board và Permission của Member
select m.Id, u.Username,p.Name
from Members m
join Boards b on b.Id=m.OwnerId
join Permissions p on p.Id=m.OwnerId
join Users u on u.Id=m.UserId
where b.Id=1

--Màn hình 9: Workspace Setting Tab
--21. SettingKey và các SettingOption tương ứng của Workspace
select sk.KeyName, so.DisplayValue
from SettingKeys sk
join SettingKeySettingOptions skso on skso.SettingKeyId=sk.Id
join SettingOptions so on so.Id=skso.SettingOptionId
join OwnerTypes ot on ot.Id=sk.OwnerTypeId AND ot.Value='WORKSPACE'

--22. SettingValue của Workspace cụ thể
select 
    sk.KeyName,
    sk.TypeValue,
    sv.Value,
    so.DisplayValue
from SettingValues sv
join SettingKeys sk on sk.Id=sv.SettingKeyId
join SettingOptions so on so.Id=sv.Value
join OwnerTypes ot on ot.Id=sk.OwnerTypeId AND ot.Value='WORKSPACE'
WHERE sv.OwnerId=1

--Màn hình 10: Setting Board
--23. SettingKey với KeyName='permission.*' với các SettingOption tương ứng cho Board
select sk.KeyName, so.DisplayValue
from SettingKeys sk
join SettingKeySettingOptions skso on skso.SettingKeyId=sk.Id
join SettingOptions so on so.Id=skso.SettingOptionId
join OwnerTypes ot on ot.Id=sk.OwnerTypeId AND ot.Value='BOARD'
where sk.KeyName like 'permissions.%'
--24. SettingKey và SettingOption tương ứng cho Board
select sk.KeyName, so.DisplayValue
from SettingKeys sk
join SettingKeySettingOptions skso on skso.SettingKeyId=sk.Id
join SettingOptions so on so.Id=skso.SettingOptionId
join OwnerTypes ot on ot.Id=sk.OwnerTypeId AND ot.Value='BOARD'
--25. SettingValue tương ứng với Board
select sv.OwnerId,sk.KeyName,sk.TypeValue,sv.Value,so.DisplayValue
from SettingValues sv
join SettingKeys sk on sk.Id=sv.SettingKeyId
join SettingOptions so on so.Id=sv.Value
join OwnerTypes ot on ot.Id=sk.OwnerTypeId AND ot.Value='BOARD'
where sv.OwnerId=3

--Màn hình 11: Workspace PowerUp
--26. Query Power-Ups được thêm vào bao nhiêu Board thuộc Workspace
--1. Lọc ra các Board thuộc Workspace (BoardId, WorkspaceId)
--2. Lọc ra các Power-Up được thêm vào Board (PowerUpId,BoardId)
--3. Liệt kê các Power-ups và số Board thuộc Workspace mà Power-ups được thêm vào
with BoardInWorkspace as
(
    select b.Id as BoardId,b.WorkspaceId
    from Boards b
    where b.WorkspaceId=1
),
PowerUpInBoards as
(
    select pu.Id PowerUpId,pu.Name,pu.IconUrl,b.Id as BoardId
    from PowerUps pu
    join BoardPowerUps bpu on bpu.PowerUpId=pu.Id
    join Boards b on b.Id=bpu.BoardId
)
select puib.Name,puib.IconUrl,COUNT(puib.BoardId) as BoardUse
from PowerUpInBoards puib 
join BoardInWorkspace biw on biw.BoardId=puib.BoardId
group by puib.Name,puib.IconUrl

--Màn hình 12: Power-Ups Detail



