-- Users
INSERT INTO Users (Username, Bio, Email, LastActive, CreatedAt, UpdatedAt, PictureUrl, FullName)
VALUES
('quang', 'Loves coding', 'quang@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'pic1.jpg', 'Quang Nguyen'),
('minh', 'Gym and travel', 'minh@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'pic2.jpg', 'Minh Tran'),
('hoa', 'Food blogger', 'hoa@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'pic3.jpg', 'Hoa Le');

-- WorkspaceType
INSERT INTO WorkspaceType (TypeName) VALUES
('Personal'),
('Team'),
('Organization');

-- Workspace
INSERT INTO Workspace (WorkspaceName, WorkspaceDescription, TypeId, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, LogoUrl)
VALUES
('Workspace A', 'Workspace for project A', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 2, 'logo1.png'),
('Workspace B', 'Workspace for project B', 2, CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, 3, 'logo2.png'),
('Workspace C', 'Workspace for project C', 3, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 1, 'logo3.png');

-- Board
INSERT INTO Board (BoardName, BoardDescription, CreatedAt, CreatedBy, BackgroundUrl, WorkspaceId, BoardStatus, UpdatedAt, UpdatedBy, IsTemplate)
VALUES
('Project Alpha', 'First project', CURRENT_TIMESTAMP, 1, 'bg1.jpg', 1, 'ACTIVE', CURRENT_TIMESTAMP, 2, 0),
('Project Beta', 'Second project', CURRENT_TIMESTAMP, 2, 'bg2.jpg', 2, 'ACTIVE', CURRENT_TIMESTAMP, 3, 1),
('Project Gamma', 'Third project', CURRENT_TIMESTAMP, 3, 'bg3.jpg', 3, 'ARCHIVE', CURRENT_TIMESTAMP, 1, 0);

-- OwnerType
INSERT INTO OwnerType (TypeName) VALUES
('workspace'),
('board'),
('user');

-- UserStarredBoard
INSERT INTO UserStarredBoard (UserId, BoardId, CreatedAt, StarredBoardsStatus)
VALUES
(1, 1, CURRENT_TIMESTAMP, 1),
(2, 2, CURRENT_TIMESTAMP, 1),
(3, 3, CURRENT_TIMESTAMP, 0);

-- UserViewHistory
INSERT INTO UserViewHistory (UserId, OwnerTypeId, OwnerId, AccessedAt)
VALUES
(1, 1, 1, CURRENT_TIMESTAMP),
(2, 2, 2, CURRENT_TIMESTAMP),
(3, 3, 3, CURRENT_TIMESTAMP);
