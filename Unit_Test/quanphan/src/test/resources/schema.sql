CREATE TABLE Users (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(255),
    Bio NVARCHAR(1000),
    Email VARCHAR(255),
    LastActive DATETIME,
    CreatedAt DATETIME,
    UpdatedAt DATETIME,
    Avatar VARCHAR(255),
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
    OwnerTypeValue VARCHAR(50)
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
-- Bảng RolePermission
CREATE TABLE RolePermission (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    PermissionName VARCHAR(50),
    PermissionCode VARCHAR(50)
);

-- Bảng Members
CREATE TABLE Members (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    UserId INT,
    RolePermissionId INT,
    OwnerTypeId INT,
    OwnerId INT,
    InvitedBy INT,
    JoinedAt TIMESTAMP,
    MemberStatus VARCHAR(50),
    FOREIGN KEY (RolePermissionId) REFERENCES RolePermission (Id),
    FOREIGN KEY (UserId) REFERENCES Users (Id),
    FOREIGN KEY (OwnerTypeId) REFERENCES OwnerType (Id)
);
-- TemplateCategory table
CREATE TABLE TemplateCategory (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    CategoryValue VARCHAR(50) NOT NULL,
    DisplayValue VARCHAR(50) NOT NULL,
    IconUrl VARCHAR(255)
);

-- Template table
CREATE TABLE Template (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255),
    TemplateDescription NVARCHAR(1000),
    CategoryId INT,
    Viewed INT,
    Copied INT,
    CreatedBy INT,
    CreatedAt TIMESTAMP,
    UpdatedAt TIMESTAMP,
    UpdatedBy INT,
    BoardId INT,
    BackgroundUrl VARCHAR(255),
    CONSTRAINT FK_TemplateCategory FOREIGN KEY (CategoryId)
        REFERENCES TemplateCategory(Id),
    CONSTRAINT FK_TemplateBoard FOREIGN KEY (BoardId)
        REFERENCES Board(Id),
    CONSTRAINT FK_TemplateCreatedBy FOREIGN KEY (CreatedBy)
        REFERENCES Users(Id),
    CONSTRAINT FK_TemplateUpdatedBy FOREIGN KEY (UpdatedBy)
        REFERENCES Users(Id)
);

-- Color table
CREATE TABLE Color (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    ColorName VARCHAR(50),
    ColorHex VARCHAR(7),
    Icon VARCHAR(255)
);

-- CardCoverType table (referenced by Cards)
CREATE TABLE CardCoverType (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(50),
    TypeValue VARCHAR(100)
);

-- AttachmentType table (referenced by Attachment)
CREATE TABLE AttachmentType (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(50),
    TypeExtension VARCHAR(10)
);

-- Stage table
CREATE TABLE Stage (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255),
    CreatedAt DATETIME,
    CreatedBy INT,
    BoardId INT,
    StageStatus VARCHAR(20),
    ColorId INT,
    Position INT,
    UpdatedAt DATETIME,
    UpdatedBy INT,
    FOREIGN KEY (BoardId) REFERENCES Board(Id),
    FOREIGN KEY (ColorId) REFERENCES Color(Id),
    FOREIGN KEY (CreatedBy) REFERENCES Users(Id),
    FOREIGN KEY (UpdatedBy) REFERENCES Users(Id)
);

-- Cards table
CREATE TABLE Cards (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    StageId INT,
    Title VARCHAR(255),
    CardDescription NVARCHAR(1000),
    CreatedAt DATETIME,
    CreatedBy INT,
    CardStatus VARCHAR(20),
    CardLocation VARCHAR(255),
    StartDate DATE,
    DueDate DATE,
    CardCoverTypeId INT,
    CoverValue VARCHAR(300),
    Position INT,
    UpdatedAt DATETIME,
    UpdatedBy INT,
    IsTemplate BIT,
    IsCompleted BIT,
    FOREIGN KEY (StageId) REFERENCES Stage(Id),
    FOREIGN KEY (CardCoverTypeId) REFERENCES CardCoverType(Id),
    FOREIGN KEY (CreatedBy) REFERENCES Users(Id),
    FOREIGN KEY (UpdatedBy) REFERENCES Users(Id)
);

-- Attachment table
CREATE TABLE Attachment (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    CardId INT,
    AttachmentTypeId INT,
    AttachmentPath VARCHAR(255),
    AttachmentName VARCHAR(255),
    CreatedAt DATETIME,
    CreatedBy INT,
    Size VARCHAR(255),
    IsCover BIT,
    Thumbnail VARCHAR(350),
    FOREIGN KEY (CardId) REFERENCES Cards(Id),
    FOREIGN KEY (AttachmentTypeId) REFERENCES AttachmentType(Id),
    FOREIGN KEY (CreatedBy) REFERENCES Users(Id)
);

-- SettingKey table
CREATE TABLE SettingKey (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    KeyName VARCHAR(100),
    SettingKeyDescription NVARCHAR(1000),
    OwnerTypeId INT,
    DefaultValue INT,
    IsBoolean BIT NOT NULL,
    FOREIGN KEY (OwnerTypeId) REFERENCES OwnerType(Id)
);

-- SettingValue table
CREATE TABLE SettingValue (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    SettingKeyId INT,
    SettingContent INT,
    CreatedAt DATETIME,
    CreatedBy INT,
    UpdatedAt DATETIME,
    UpdatedBy INT,
    OwnerId INT,
    FOREIGN KEY (SettingKeyId) REFERENCES SettingKey(Id),
    FOREIGN KEY (CreatedBy) REFERENCES Users(Id),
    FOREIGN KEY (UpdatedBy) REFERENCES Users(Id)
);

-- SettingOption table
CREATE TABLE SettingOption (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    DisplayValue VARCHAR(255),
    SettingOptionValue VARCHAR(50)
);

-- SettingKeySettingOption junction table
CREATE TABLE SettingKeySettingOption (
    SettingKeyId INT NOT NULL,
    SettingOptionId INT NOT NULL,
    FOREIGN KEY (SettingKeyId) REFERENCES SettingKey(Id),
    FOREIGN KEY (SettingOptionId) REFERENCES SettingOption(Id)
);
