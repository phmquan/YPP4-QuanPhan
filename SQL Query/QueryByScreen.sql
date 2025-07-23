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


    

