-- Query này lấy thông tin về các boards (bảng/dự án) mà user có ID = 1 có quyền truy cập thông qua các workspace họ đã tạo.
SELECT b.Name, b.Description, b.CreatedAt, b.CreatedBy, b.AccessedAt, b.IsStar,b.BackgroundUrl,b.WorkspaceId,b.Status, u.id as UserId, u.Username as UserName
FROM Users u
Join Workspaces w ON w.CreatedBy=u.Id
Join Boards b on b.WorkspaceId=w.Id
WHERE
    u.Id=1

-- Query lấy ra Workspace mà user ID = 1 là member
SELECT w.Name,w.Type,w.Description,w.CreatedAt,u.Id as UserId, u.Username as UserName,
       m.PermissionId
FROM Members m
JOIN Workspaces w ON w.Id = m.OwnerId
JOIN Users u ON u.Id = m.UserId
WHERE m.UserId = 1 
  AND m.OwnerType = 'WORKSPACE' 

-- Query lấy ra Board mà user ID = 1 là member
SELECT b.Name, b.Description, b.CreatedAt, b.CreatedBy, b.AccessedAt, b.IsStar,
       b.BackgroundUrl, b.WorkspaceId, b.Status, 
       u.Id as UserId, u.Username as UserName,
       m.PermissionId
FROM Members m
JOIN Boards b ON b.Id = m.OwnerId
JOIN Users u ON u.Id = m.UserId
WHERE m.UserId = 1 
  AND m.OwnerType = 'BOARD'

-- Query lấy ra 10 template categories
SELECT TOP 10 Name,IconUrl
FROM TemplateCategories 

-- Query lấy ra New and notable templates
SELECT t.Title,t.BackgroundUrl,t.CreatedAt,t.CreatedBy,t.Copied,t.Viewed,t.Description
From 
    Templates t
ORDER BY
    CreatedAT desc, Viewed desc, Copied desc

-- Query tạo Workspace
INSERT INTO WORKSPACES (Name,Type,Description,CreatedBy) VALUES ('BBV Workspace','IT','This workspace is for bbv VietNam',1)

-- Query lấy tất cả Board trong Workspace có id = 1
SELECT b.Name,b.BackgroundUrl,b.AccessedAt
FROM Boards b
JOIN Workspaces w ON w.Id=b.WorkspaceId
WHERE
    w.Id=1

-- Query lấy tất cả member và permission của member đó trong Workspace id =1
INSERT INTO Members (UserId,PermissionId,OwnerType,OwnerId) VALUES (2,1,'BOARD',1)

SELECT u.UserName as Username, u.LastActive as UserLastActive,p.Name as PermissionName,p.Code as PermissionCode,b.Id as BoardId,b.Name as BoardName,b.BackgroundUrl as BoardBackground
FROM Workspaces w
Join Members m ON m.OwnerId=w.Id
Join Boards b ON b.WorkspaceId=w.Id
Join Permissions p ON m.PermissionId=p.Id
Join Users u ON m.UserId=u.Id
WHERE m.OwnerType='BOARD' AND w.Id=1

-- Query lấy sharelink Workspace id = 2
SELECT * 
FROM ShareLinks s
JOIN Permissions p ON s.PermissionId=p.Id
WHERE s.OwnerType='WORKSPACE' AND s.OwnerId=2

-- Query update permision cho ShareLink Workspace id = 2
UPDATE SL
SET SL.PermissionId = P.Id
FROM dbo.ShareLinks SL
INNER JOIN dbo.Permissions P ON P.Name = 'Admin'
WHERE SL.OwnerType = 'WORKSPACE' 
  AND SL.OwnerId = 2;

SELECT * 
FROM ShareLinks s
JOIN Permissions p ON s.PermissionId=p.Id
WHERE s.OwnerType='WORKSPACE' AND s.OwnerId=2

-- Query update status sharelink cho workspace id = 2


