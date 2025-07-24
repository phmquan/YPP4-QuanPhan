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

-- Màn hình 4 Create Workspace Slide 7
Insert INTO Workspaces (Name, Description,Type) VALUES ('','','')








-- Lấy thông tin về các boards mà user có id= :userId có quyền truy cập thông qua workspace họ tạo
SELECT 
    b.Name, 
    b.Description, 
    b.CreatedAt, 
    b.CreatedBy, 
    b.AccessedAt, 
    b.IsStar,
    b.BackgroundUrl,
    b.WorkspaceId,
    b.Status, 
    u.Id AS UserId, 
    u.Username AS UserName
FROM Users u
    JOIN Workspaces w ON w.CreatedBy = u.Id
    JOIN Boards b ON b.WorkspaceId = w.Id
WHERE u.Id = :userId;

-- Lấy ra Board mà user ID = 1 là member
SELECT 
    b.Name, 
    b.Description, 
    b.CreatedAt, 
    b.CreatedBy, 
    b.AccessedAt, 
    b.IsStar,
    b.BackgroundUrl, 
    b.WorkspaceId, 
    b.Status, 
    u.Id AS UserId, 
    u.Username AS UserName,
    m.PermissionId
FROM Members m
    JOIN Boards b ON b.Id = m.OwnerId
    JOIN Users u ON u.Id = m.UserId
WHERE m.UserId = 1 
    AND m.OwnerType = 'BOARD';

-- Lấy tất cả Board trong Workspace có id = 1
SELECT 
    b.Name,
    b.BackgroundUrl,
    b.AccessedAt
FROM Boards b
    JOIN Workspaces w ON w.Id = b.WorkspaceId
WHERE w.Id = 1;

-- =================================================================
-- WORKSPACE QUERIES
-- =================================================================

-- Lấy ra Workspace mà user ID = 1 là member
SELECT 
    w.Name,
    w.Type,
    w.Description,
    w.CreatedAt,
    u.Id AS UserId, 
    u.Username AS UserName,
    m.PermissionId
FROM Members m
    JOIN Workspaces w ON w.Id = m.OwnerId
    JOIN Users u ON u.Id = m.UserId
WHERE m.UserId = 1 
    AND m.OwnerType = 'WORKSPACE';

-- Tạo Workspace mới
INSERT INTO Workspaces (Name, Type, Description, CreatedBy) 
VALUES ('BBV Workspace', 'IT', 'This workspace is for bbv VietNam', 1);

-- =================================================================
-- MEMBER & PERMISSION QUERIES
-- =================================================================

-- Thêm member vào Board
INSERT INTO Members (UserId, PermissionId, OwnerType, OwnerId) 
VALUES (2, 1, 'BOARD', 1);

-- Lấy tất cả member và permission trong Workspace id = 1
SELECT 
    u.UserName AS Username, 
    u.LastActive AS UserLastActive,
    p.Name AS PermissionName,
    p.Code AS PermissionCode,
    b.Id AS BoardId,
    b.Name AS BoardName,
    b.BackgroundUrl AS BoardBackground
FROM Workspaces w
    JOIN Members m ON m.OwnerId = w.Id
    JOIN Boards b ON b.WorkspaceId = w.Id
    JOIN Permissions p ON m.PermissionId = p.Id
    JOIN Users u ON m.UserId = u.Id
WHERE m.OwnerType = 'BOARD' 
    AND w.Id = 1;

-- Lấy tất cả member và permission trong Board id = 1
SELECT 
    u.Id, 
    u.Username, 
    p.Name AS PermissionName
FROM Members m
    JOIN Users u ON m.UserId = u.Id
    JOIN Permissions p ON m.PermissionId = p.Id
    JOIN Boards b ON m.OwnerId = b.Id
WHERE m.OwnerType = 'BOARD' 
    AND b.Id = 1;

-- Đếm số member trong Board id = 1
SELECT COUNT(m.Id) AS BoardMember
FROM Members m
    JOIN Boards b ON m.OwnerId = b.Id
WHERE m.OwnerType = 'BOARD' 
    AND b.Id = 1;

-- =================================================================
-- SHARELINK QUERIES
-- =================================================================

-- Lấy sharelink của Workspace id = 2
SELECT 
    s.*,
    p.Name AS PermissionName
FROM ShareLinks s
    JOIN Permissions p ON s.PermissionId = p.Id
WHERE s.OwnerType = 'WORKSPACE' 
    AND s.OwnerId = 2;

-- Update permission cho ShareLink Workspace id = 2
UPDATE SL
SET SL.PermissionId = P.Id
FROM ShareLinks SL
    INNER JOIN Permissions P ON P.Name = 'Admin'
WHERE SL.OwnerType = 'WORKSPACE' 
    AND SL.OwnerId = 2;

-- Update status sharelink cho Workspace id = 2
UPDATE sl
SET sl.Status = 'ACTIVE'
FROM ShareLinks sl
    JOIN Workspaces w ON w.Id = sl.OwnerId
WHERE sl.OwnerType = 'WORKSPACE' 
    AND w.Id = 2;


-- =================================================================
-- SETTINGS QUERIES
-- =================================================================

-- Lấy tất cả SettingKey, giá trị mặc định và SettingOption
SELECT 
    sk.KeyName,
    sk.Description, 
    sk.DefaultValue, 
    sk.TypeValue,
    so.DisplayValue
FROM SettingKeys sk
    JOIN SettingKeySettingOptions skso ON sk.Id = skso.SettingKeyId
    JOIN SettingOptions so ON so.Id = skso.SettingOptionId
    JOIN SettingValues sv ON sv.SettingKeyId = sk.Id
WHERE sk.OwnerType = 'WORKSPACE'; -- Có thể thay bằng 'USER' hoặc 'BOARD'

-- Lấy tất cả SettingKey và SettingValue của Workspace có id = 2
SELECT 
    sk.KeyName,
    sk.Description,
    sv.Value
FROM SettingKeys sk
    JOIN SettingValues sv ON sv.SettingKeyId = sk.Id
WHERE sk.OwnerType = 'WORKSPACE' 
    AND sv.OwnerId = 2;