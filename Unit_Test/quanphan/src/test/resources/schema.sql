CREATE TABLE Users (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(255),
    Bio NVARCHAR(1000),
    Email VARCHAR(255),
    LastActive DATETIME,
    CreatedAt DATETIME,
    UpdatedAt DATETIME,
    PictureUrl VARCHAR(255),
    FullName NCHAR(100)
);

CREATE TABLE WorkspaceType (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(255)
);

CREATE TABLE Workspace (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    WorkspaceName VARCHAR(255),
    WorkspaceDescription NVARCHAR(1000),
    TypeId INT,
    CreatedAt DATETIME,
    CreatedBy INT,
    UpdatedAt DATETIME,
    UpdatedBy INT,
    LogoUrl VARCHAR(500),
    FOREIGN KEY (TypeId) REFERENCES WorkspaceType(Id),
    FOREIGN KEY (CreatedBy) REFERENCES Users(Id),
    FOREIGN KEY (UpdatedBy) REFERENCES Users(Id)
);

CREATE TABLE Board (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    BoardName VARCHAR(255),
    BoardDescription NVARCHAR(1000),
    CreatedAt DATETIME,
    CreatedBy INT,
    BackgroundUrl VARCHAR(2000),
    WorkspaceId INT,
    BoardStatus VARCHAR(50),
    UpdatedAt DATETIME,
    UpdatedBy INT,
    IsTemplate BIT,
    FOREIGN KEY (CreatedBy) REFERENCES Users(Id),
    FOREIGN KEY (UpdatedBy) REFERENCES Users(Id),
    FOREIGN KEY (WorkspaceId) REFERENCES Workspace(Id)
);

CREATE TABLE OwnerType (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(50)
);

CREATE TABLE UserStarredBoard (
    UserId INT NOT NULL,
    BoardId INT NOT NULL,
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    StarredBoardsStatus BIT NOT NULL,
    FOREIGN KEY (BoardId) REFERENCES Board(Id),
    FOREIGN KEY (UserId) REFERENCES Users(Id)
);

CREATE TABLE UserViewHistory (
    UserId INT NOT NULL,
    OwnerTypeId INT,
    OwnerId INT NOT NULL,
    AccessedAt DATETIME,
    FOREIGN KEY (UserId) REFERENCES Users(Id),
    FOREIGN KEY (OwnerTypeId) REFERENCES OwnerType(Id)
);
