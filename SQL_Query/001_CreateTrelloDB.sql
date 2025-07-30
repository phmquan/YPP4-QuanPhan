CREATE DATABASE Trello;
GO

USE [Trello];
GO

-- Users table
CREATE TABLE [dbo].[Users](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Username] [varchar](255) NULL,
    [Bio] [text] NULL,
    [Email] [varchar](255) NULL,
    [LastActive] [datetime] NULL,
    [CreatedAt] [datetime] NULL,
    [PictureUrl] [varchar](max) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Workspaces table
CREATE TABLE [dbo].[Workspaces](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [WorkspaceName] [varchar](50) NULL,
    [WorkspaceDescription] [varchar](255) NULL,
    [WorkspaceType] [varchar](100) NULL,
    [CreatedAt] [datetime] NULL,
    [CreatedBy] [int] NULL,
    [IconUrl] [varchar](255) NULL,
    [UpdatedAt] [datetime] NULL,
    [UpdatedBy] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Boards table
CREATE TABLE [dbo].[Boards](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [BoardName] [varchar](50) NULL,
    [BoardDescription] [text] NULL,
    [CreatedAt] [datetime] NULL,
    [CreatedBy] [int] NULL,
    [AccessedAt] [datetime] NULL,
    [IsStar] [bit] NULL,
    [BackgroundUrl] [varchar](500) NULL,
    [WorkspaceId] [int] NULL,
    [BoardStatus] [varchar](50) NULL,
    [UpdatedAt] [datetime] NULL,
    [UpdatedBy] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Colors table
CREATE TABLE [dbo].[Colors](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [ColorName] [text] NULL,
    [Icon] [text] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Stages table
CREATE TABLE [dbo].[Stages](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Title] [varchar](255) NULL,
    [CreatedAt] [datetime] NULL,
    [BoardId] [int] NULL,
    [StageStatus] [varchar](20) NULL,
    [ColorId] [int] NULL,
    [Position] [int] NULL,
    [UpdatedAt] [datetime] NULL,
    [UpdatedBy] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Cards table
CREATE TABLE [dbo].[Cards](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [StageId] [int] NULL,
    [Title] [varchar](50) NULL,
    [CardDescription] [text] NULL,
    [CreatedAt] [datetime] NULL,
    [CardStatus] [varchar](20) NULL,
    [CardLocation] [varchar](255) NULL,
    [StartDate] [date] NULL,
    [DueDate] [date] NULL,
    [CoverType] [varchar](50) NULL,
    [CoverValue] [varchar](255) NULL,
    [Position] [int] NULL,
    [UpdatedAt] [datetime] NULL,
    [UpdatedBy] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Labels table
CREATE TABLE [dbo].[Labels](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Title] [varchar](100) NULL,
    [ColorId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- CardLabels junction table
CREATE TABLE [dbo].[CardLabels](
    [CardId] [int] NOT NULL,
    [LabelId] [int] NOT NULL,
    PRIMARY KEY CLUSTERED ([CardId] ASC, [LabelId] ASC)
);

-- CheckLists table
CREATE TABLE [dbo].[CheckLists](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CheckListName] [varchar](50) NULL,
    [CardId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- RolePermissions table
CREATE TABLE [dbo].[RolePermissions](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [PermissionName] [varchar](50) NULL,
    [PermissionCode] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- OwnerTypes table
CREATE TABLE [dbo].[OwnerTypes](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [OwnerTypeValue] [varchar](50) NOT NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Members table
CREATE TABLE [dbo].[Members](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [UserId] [int] NULL,
    [PermissionId] [int] NULL,
    [OwnerTypeId] [int] NULL,
    [OwnerId] [int] NULL,
    [InvitedBy] [int] NULL,
    [JoinedAt] [datetime] NULL,
    [Status] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- CheckListItems table
CREATE TABLE [dbo].[CheckListItems](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CheckListItemName] [varchar](50) NULL,
    [MemberId] [int] NULL,
    [CheckListId] [int] NULL,
    [DueDate] [date] NULL,
    [CheckListItemStatus] [bit] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Attachments table
CREATE TABLE [dbo].[Attachments](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CardId] [int] NULL,
    [Link] [varchar](255) NULL,
    [FileType] [varchar](50) NULL,
    [FilePath] [varchar](255) NULL,
    [AttachmentName] [varchar](100) NULL,
    [UploadAt] [datetime] NULL,
    [UploadBy] [int] NULL,
    [IsCover] [bit] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Comments table
CREATE TABLE [dbo].[Comments](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Content] [text] NULL,
    [CardId] [int] NULL,
    [CreatedAt] [datetime] NULL,
    [CreatedBy] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Reactions table
CREATE TABLE [dbo].[Reactions](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Icon] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- CommentReactions junction table
CREATE TABLE [dbo].[CommentReactions](
    [CommentId] [int] NOT NULL,
    [ReactionId] [int] NOT NULL,
    [CreatedBy] [int] NULL,
    PRIMARY KEY CLUSTERED ([CommentId] ASC, [ReactionId] ASC)
);

-- MemberReactions junction table
CREATE TABLE [dbo].[MemberReactions](
    [MemberId] [int] NOT NULL,
    [ReactionId] [int] NOT NULL,
    PRIMARY KEY CLUSTERED ([MemberId] ASC, [ReactionId] ASC)
);

-- Activities table
CREATE TABLE [dbo].[Activities](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CreatedAt] [datetime] NULL,
    [ActivityDescription] [text] NULL,
    [UserId] [int] NULL,
    [OwnerTypeId] [int] NULL,
    [OwnerId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Notifications table
CREATE TABLE [dbo].[Notifications](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [ActivityId] [int] NULL,
    [NotificationStatus] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Collections table
CREATE TABLE [dbo].[Collections](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CollectionName] [varchar](50) NULL,
    [CreatedBy] [int] NULL,
    [CreatedAt] [datetime] NULL,
    [WorkspaceId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- BoardCollections junction table
CREATE TABLE [dbo].[BoardCollections](
    [BoardId] [int] NOT NULL,
    [CollectionId] [int] NOT NULL,
    PRIMARY KEY CLUSTERED ([BoardId] ASC, [CollectionId] ASC)
);

-- BoardUsers junction table
CREATE TABLE [dbo].[BoardUsers](
    [BoardId] [int] NOT NULL,
    [UserID] [int] NOT NULL,
    [AccessedAt] [datetime] NULL,
    PRIMARY KEY CLUSTERED ([BoardId] ASC, [UserID] ASC)
);

-- PowerUpCategories table
CREATE TABLE [dbo].[PowerUpCategories](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CategoryName] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- PowerUps table
CREATE TABLE [dbo].[PowerUps](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [PowerUpName] [varchar](50) NULL,
    [IconUrl] [varchar](300) NULL,
    [BackgroundUrl] [varchar](300) NULL,
    [AuthorName] [varchar](50) NULL,
    [PowerUpDescription] [text] NULL,
    [EmailContact] [varchar](255) NULL,
    [PolicyUrl] [varchar](300) NULL,
    [IsStaffPick] [bit] NULL,
    [IsIntegration] [bit] NULL,
    [PowerUpCategoryId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- BoardPowerUps junction table
CREATE TABLE [dbo].[BoardPowerUps](
    [BoardId] [int] NOT NULL,
    [PowerUpId] [int] NOT NULL,
    PRIMARY KEY CLUSTERED ([BoardId] ASC, [PowerUpId] ASC)
);

-- Stickers table
CREATE TABLE [dbo].[Stickers](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [StickerName] [varchar](50) NULL,
    [StickerUrl] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- CardStickers junction table
CREATE TABLE [dbo].[CardStickers](
    [CardId] [int] NOT NULL,
    [StickerId] [int] NOT NULL,
    [PositionX] [float] NULL,
    [PositionY] [float] NULL,
    [IndexZ] [int] NULL,
    PRIMARY KEY CLUSTERED ([CardId] ASC, [StickerId] ASC)
);

-- DataTypes table
CREATE TABLE [dbo].[DataTypes](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [TypeValue] [varchar](20) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- CustomFields table
CREATE TABLE [dbo].[CustomFields](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Title] [varchar](50) NULL,
    [DataTypeId] [int] NULL,
    [BoardId] [int] NULL,
    [Position] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- FieldItems table
CREATE TABLE [dbo].[FieldItems](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [ColorId] [int] NULL,
    [FieldItemValue] [varchar](50) NULL,
    [Priority] [int] NULL,
    [CustomFieldId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- FieldValues table
CREATE TABLE [dbo].[FieldValues](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CardId] [int] NULL,
    [FieldValue] [varchar](50) NULL,
    [CustomFieldId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- ShareLinks table
CREATE TABLE [dbo].[ShareLinks](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [OwnerTypeId] [int] NULL,
    [OwnerId] [int] NULL,
    [PermissionId] [int] NULL,
    [ShareLinkToken] [varchar](255) NULL,
    [ShareLinkStatus] [bit] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- SettingOptions table
CREATE TABLE [dbo].[SettingOptions](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [DisplayValue] [varchar](255) NULL,
    [SettingOptionValue] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- SettingKeys table
CREATE TABLE [dbo].[SettingKeys](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [KeyName] [varchar](100) NULL,
    [Description] [text] NULL,
    [OwnerTypeId] [int] NULL,
    [DefaultValue] [int] NULL,
    [DataTypeId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- SettingKeySettingOptions junction table
CREATE TABLE [dbo].[SettingKeySettingOptions](
    [SettingKeyId] [int] NOT NULL,
    [SettingOptionId] [int] NOT NULL,
    PRIMARY KEY CLUSTERED ([SettingKeyId] ASC, [SettingOptionId] ASC)
);

-- SettingValues table
CREATE TABLE [dbo].[SettingValues](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [SettingKeyId] [int] NULL,
    [SettingContent] [int] NULL,
    [OwnerId] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- BillingContacts table
CREATE TABLE [dbo].[BillingContacts](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [UserId] [int] NULL,
    [WorkspaceId] [int] NULL,
    [BillingContactName] [varchar](50) NULL,
    [BillingContactEmail] [varchar](100) NULL,
    [BillingLanguage] [int] NULL,
    [AdditionalInvoiceDetail] [varchar](250) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- BillingPlans table
CREATE TABLE [dbo].[BillingPlans](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [PlanName] [varchar](100) NULL,
    [BIllingPlanType] [varchar](50) NULL,
    [PricePerUser] [decimal](10, 2) NULL,
    [BillingPlanStatus] [varchar](50) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- PaymentInformations table
CREATE TABLE [dbo].[PaymentInformations](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [BillingContactId] [int] NULL,
    [CardNumber] [varchar](20) NULL,
    [CardBrand] [varchar](50) NULL,
    [ExpirationDate] [varchar](20) NULL,
    [Cvv] [varchar](10) NULL,
    [Country] [varchar](100) NULL,
    [PostalCode] [varchar](20) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Subscriptions table
CREATE TABLE [dbo].[Subscriptions](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [BillingContactId] [int] NULL,
    [BillingPlanId] [int] NULL,
    [StartDate] [date] NULL,
    [EndDate] [date] NULL,
    [BillingCycle] [varchar](20) NULL,
    [SubscriptionStatus] [varchar](50) NULL,
    [AutoRenew] [bit] NULL,
    [MemberCountBilled] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- TemplateCategories table
CREATE TABLE [dbo].[TemplateCategories](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CategoryName] [varchar](50) NULL,
    [IconUrl] [varchar](max) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Templates table
CREATE TABLE [dbo].[Templates](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Title] [varchar](255) NULL,
    [TemplateDescription] [text] NULL,
    [TemplateCategoryId] [int] NULL,
    [Viewed] [int] NULL,
    [Copied] [int] NULL,
    [CreatedBy] [int] NULL,
    [CreatedAt] [datetime] NULL,
    [BoardId] [int] NULL,
    [BackgroundUrl] [varchar](max) NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- Exports table
CREATE TABLE [dbo].[Exports](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [WorkspaceId] [int] NULL,
    [CreatedBy] [int] NULL,
    [CreatedAt] [datetime] NULL,
    [Size] [int] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- WorkspaceMembershipDomains table
CREATE TABLE [dbo].[WorkspaceMembershipDomains](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [WorkspaceId] [int] NOT NULL,
    [EmailDomain] [text] NOT NULL,
    [CreatedAt] [datetime] NULL,
    PRIMARY KEY CLUSTERED ([Id] ASC)
);

-- FOREIGN KEY CONSTRAINTS

-- Workspaces
ALTER TABLE [dbo].[Workspaces] ADD CONSTRAINT [FK_Workspaces_CreatedBy_Users] 
    FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id]);
ALTER TABLE [dbo].[Workspaces] ADD CONSTRAINT [FK_Workspaces_UpdatedBy_Users] 
    FOREIGN KEY([UpdatedBy]) REFERENCES [dbo].[Users] ([Id]);

-- Boards
ALTER TABLE [dbo].[Boards] ADD CONSTRAINT [FK_Boards_CreatedBy_Users] 
    FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id]);
ALTER TABLE [dbo].[Boards] ADD CONSTRAINT [FK_Boards_UpdatedBy_Users] 
    FOREIGN KEY([UpdatedBy]) REFERENCES [dbo].[Users] ([Id]);
ALTER TABLE [dbo].[Boards] ADD CONSTRAINT [FK_Boards_Workspaces] 
    FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id]);

-- Stages
ALTER TABLE [dbo].[Stages] ADD CONSTRAINT [FK_Stages_BoardId_Boards] 
    FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id]);
ALTER TABLE [dbo].[Stages] ADD CONSTRAINT [FK_Stages_ColorId_Colors] 
    FOREIGN KEY([ColorId]) REFERENCES [dbo].[Colors] ([Id]);
ALTER TABLE [dbo].[Stages] ADD CONSTRAINT [FK_Stages_UpdatedBy_Users] 
    FOREIGN KEY([UpdatedBy]) REFERENCES [dbo].[Users] ([Id]);

-- Cards
ALTER TABLE [dbo].[Cards] ADD CONSTRAINT [FK_Cards_StageId_Stages] 
    FOREIGN KEY([StageId]) REFERENCES [dbo].[Stages] ([Id]);
ALTER TABLE [dbo].[Cards] ADD CONSTRAINT [FK_Cards_UpdatedBy_Users] 
    FOREIGN KEY([UpdatedBy]) REFERENCES [dbo].[Users] ([Id]);

-- Labels
ALTER TABLE [dbo].[Labels] ADD CONSTRAINT [FK_Labels_ColorId_Colors] 
    FOREIGN KEY([ColorId]) REFERENCES [dbo].[Colors] ([Id]);

-- CardLabels
ALTER TABLE [dbo].[CardLabels] ADD CONSTRAINT [FK_CardLabels_CardId_Cards] 
    FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id]);
ALTER TABLE [dbo].[CardLabels] ADD CONSTRAINT [FK_CardLabels_LabelId_Labels] 
    FOREIGN KEY([LabelId]) REFERENCES [dbo].[Labels] ([Id]);

-- CheckLists
ALTER TABLE [dbo].[CheckLists] ADD CONSTRAINT [FK_CheckLists_CardId_Cards] 
    FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id]);

-- Members
ALTER TABLE [dbo].[Members] ADD CONSTRAINT [FK_Members_UserId_Users] 
    FOREIGN KEY([UserId]) REFERENCES [dbo].[Users] ([Id]);
ALTER TABLE [dbo].[Members] ADD CONSTRAINT [FK_Members_PermissionId_RolePermissions] 
    FOREIGN KEY([PermissionId]) REFERENCES [dbo].[RolePermissions] ([Id]);
ALTER TABLE [dbo].[Members] ADD CONSTRAINT [FK_Members_OwnerTypeId_OwnerTypes] 
    FOREIGN KEY([OwnerTypeId]) REFERENCES [dbo].[OwnerTypes] ([Id]);

-- CheckListItems
ALTER TABLE [dbo].[CheckListItems] ADD CONSTRAINT [FK_CheckListItems_MemberId_Members] 
    FOREIGN KEY([MemberId]) REFERENCES [dbo].[Members] ([Id]);
ALTER TABLE [dbo].[CheckListItems] ADD CONSTRAINT [FK_CheckListItems_CheckListId_CheckLists] 
    FOREIGN KEY([CheckListId]) REFERENCES [dbo].[CheckLists] ([Id]);

-- Attachments
ALTER TABLE [dbo].[Attachments] ADD CONSTRAINT [FK_Attachments_CardId_Cards] 
    FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id]);
ALTER TABLE [dbo].[Attachments] ADD CONSTRAINT [FK_Attachments_UploadBy_Users] 
    FOREIGN KEY([UploadBy]) REFERENCES [dbo].[Users] ([Id]);

-- Comments
ALTER TABLE [dbo].[Comments] ADD CONSTRAINT [FK_Comments_CardId_Cards] 
    FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id]);
ALTER TABLE [dbo].[Comments] ADD CONSTRAINT [FK_Comments_CreatedBy_Users] 
    FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id]);

-- CommentReactions
ALTER TABLE [dbo].[CommentReactions] ADD CONSTRAINT [FK_CommentReactions_CommentId_Comments] 
    FOREIGN KEY([CommentId]) REFERENCES [dbo].[Comments] ([Id]);
ALTER TABLE [dbo].[CommentReactions] ADD CONSTRAINT [FK_CommentReactions_ReactionId_Reactions] 
    FOREIGN KEY([ReactionId]) REFERENCES [dbo].[Reactions] ([Id]);
ALTER TABLE [dbo].[CommentReactions] ADD CONSTRAINT [FK_CommentReactions_CreatedBy_Members] 
    FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Members] ([Id]);

-- MemberReactions
ALTER TABLE [dbo].[MemberReactions] ADD CONSTRAINT [FK_MemberReactions_MemberId_Members] 
    FOREIGN KEY([MemberId]) REFERENCES [dbo].[Members] ([Id]);
ALTER TABLE [dbo].[MemberReactions] ADD CONSTRAINT [FK_MemberReactions_ReactionId_Reactions] 
    FOREIGN KEY([ReactionId]) REFERENCES [dbo].[Reactions] ([Id]);

-- Activities
ALTER TABLE [dbo].[Activities] ADD CONSTRAINT [FK_Activities_UserId_Users] 
    FOREIGN KEY([UserId]) REFERENCES [dbo].[Users] ([Id]);
ALTER TABLE [dbo].[Activities] ADD CONSTRAINT [FK_Activities_OwnerTypeId_OwnerTypes] 
    FOREIGN KEY([OwnerTypeId]) REFERENCES [dbo].[OwnerTypes] ([Id]);

-- Notifications
ALTER TABLE [dbo].[Notifications] ADD CONSTRAINT [FK_Notifications_ActivityId_Activities] 
    FOREIGN KEY([ActivityId]) REFERENCES [dbo].[Activities] ([Id]);

-- Collections
ALTER TABLE [dbo].[Collections] ADD CONSTRAINT [FK_Collections_CreatedBy_Users] 
    FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id]);
ALTER TABLE [dbo].[Collections] ADD CONSTRAINT [FK_Collections_WorkspaceId_Workspaces] 
    FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id]);

-- BoardCollections
ALTER TABLE [dbo].[BoardCollections] ADD CONSTRAINT [FK_BoardCollections_BoardId_Boards] 
    FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id]);
ALTER TABLE [dbo].[BoardCollections] ADD CONSTRAINT [FK_BoardCollections_CollectionId_Collections] 
    FOREIGN KEY([CollectionId]) REFERENCES [dbo].[Collections] ([Id]);

-- BoardUsers
ALTER TABLE [dbo].[BoardUsers] ADD CONSTRAINT [FK_BoardUsers_BoardId_Boards] 
    FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id]) ON DELETE CASCADE;
ALTER TABLE [dbo].[BoardUsers] ADD CONSTRAINT [FK_BoardUsers_UserID_Users] 
    FOREIGN KEY([UserID]) REFERENCES [dbo].[Users] ([Id]) ON DELETE CASCADE;

-- PowerUps
ALTER TABLE [dbo].[PowerUps] ADD CONSTRAINT [FK_PowerUps_PowerUpCategoryId_PowerUpCategories] 
    FOREIGN KEY([PowerUpCategoryId]) REFERENCES [dbo].[PowerUpCategories] ([Id]);

-- BoardPowerUps
ALTER TABLE [dbo].[BoardPowerUps] ADD CONSTRAINT [FK_BoardPowerUps_BoardId_Boards] 
    FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id]);
ALTER TABLE [dbo].[BoardPowerUps] ADD CONSTRAINT [FK_BoardPowerUps_PowerUpId_PowerUps] 
    FOREIGN KEY([PowerUpId]) REFERENCES [dbo].[PowerUps] ([Id]);

-- CardStickers
ALTER TABLE [dbo].[CardStickers] ADD CONSTRAINT [FK_CardStickers_CardId_Cards] 
    FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id]);
ALTER TABLE [dbo].[CardStickers] ADD CONSTRAINT [FK_CardStickers_StickerId_Stickers] 
    FOREIGN KEY([StickerId]) REFERENCES [dbo].[Stickers] ([Id]);

-- CustomFields
ALTER TABLE [dbo].[CustomFields] ADD CONSTRAINT [FK_CustomFields_DataTypeId_DataTypes] 
    FOREIGN KEY([DataTypeId]) REFERENCES [dbo].[DataTypes] ([Id]);

-- FieldItems
ALTER TABLE [dbo].[FieldItems] ADD CONSTRAINT [FK_FieldItems_ColorId_Colors] 
    FOREIGN KEY([ColorId]) REFERENCES [dbo].[Colors] ([Id]);
ALTER TABLE [dbo].[FieldItems] ADD CONSTRAINT [FK_FieldItems_CustomFieldId_CustomFields] 
    FOREIGN KEY([CustomFieldId]) REFERENCES [dbo].[CustomFields] ([Id]);

-- FieldValues
ALTER TABLE [dbo].[FieldValues] ADD CONSTRAINT [FK_FieldValues_CardId_Cards] 
    FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id]);
ALTER TABLE [dbo].[FieldValues] ADD CONSTRAINT [FK_FieldValues_CustomFieldId_CustomFields] 
    FOREIGN KEY([CustomFieldId]) REFERENCES [dbo].[CustomFields] ([Id]);

-- ShareLinks
ALTER TABLE [dbo].[ShareLinks] ADD CONSTRAINT [FK_ShareLinks_OwnerTypeId_OwnerTypes] 
    FOREIGN KEY([OwnerTypeId]) REFERENCES [dbo].[OwnerTypes] ([Id]);
ALTER TABLE [dbo].[ShareLinks] ADD CONSTRAINT [FK_ShareLinks_PermissionId_RolePermissions] 
    FOREIGN KEY([PermissionId]) REFERENCES [dbo].[RolePermissions] ([Id]);

-- SettingKeys
ALTER TABLE [dbo].[SettingKeys] ADD CONSTRAINT [FK_SettingKeys_OwnerTypeId_OwnerTypes] 
    FOREIGN KEY([OwnerTypeId]) REFERENCES [dbo].[OwnerTypes] ([Id]);
ALTER TABLE [dbo].[SettingKeys] ADD CONSTRAINT [FK_SettingKeys_DataTypeId_DataTypes] 
    FOREIGN KEY([DataTypeId]) REFERENCES [dbo].[DataTypes] ([Id]);

-- SettingKeySettingOptions
ALTER TABLE [dbo].[SettingKeySettingOptions] ADD CONSTRAINT [FK_SettingKeySettingOptions_SettingKeyId_SettingKeys] 
    FOREIGN KEY([SettingKeyId]) REFERENCES [dbo].[SettingKeys] ([Id]);
ALTER TABLE [dbo].[SettingKeySettingOptions] ADD CONSTRAINT [FK_SettingKeySettingOptions_SettingOptionId_SettingOptions] 
    FOREIGN KEY([SettingOptionId]) REFERENCES [dbo].[SettingOptions] ([Id]);

-- SettingValues
ALTER TABLE [dbo].[SettingValues] ADD CONSTRAINT [FK_SettingValues_SettingKeyId_SettingKeys] 
    FOREIGN KEY([SettingKeyId]) REFERENCES [dbo].[SettingKeys] ([Id]);

-- BillingContacts
ALTER TABLE [dbo].[BillingContacts] ADD CONSTRAINT [FK_BillingContacts_UserId_Users] 
    FOREIGN KEY([UserId]) REFERENCES [dbo].[Users] ([Id]);
ALTER TABLE [dbo].[BillingContacts] ADD CONSTRAINT [FK_BillingContacts_WorkspaceId_Workspaces] 
    FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id]);
ALTER TABLE [dbo].[BillingContacts] ADD CONSTRAINT [FK_BillingContacts_BillingLanguage_SettingOptions] 
    FOREIGN KEY([BillingLanguage]) REFERENCES [dbo].[SettingOptions] ([Id]);

-- PaymentInformations
ALTER TABLE [dbo].[PaymentInformations] ADD CONSTRAINT [FK_PaymentInformations_BillingContactId_BillingContacts] 
    FOREIGN KEY([BillingContactId]) REFERENCES [dbo].[BillingContacts] ([Id]);

-- Subscriptions
ALTER TABLE [dbo].[Subscriptions] ADD CONSTRAINT [FK_Subscriptions_BillingContactId_BillingContacts] 
    FOREIGN KEY([BillingContactId]) REFERENCES [dbo].[BillingContacts] ([Id]);
ALTER TABLE [dbo].[Subscriptions] ADD CONSTRAINT [FK_Subscriptions_BillingPlanId_BillingPlans] 
    FOREIGN KEY([BillingPlanId]) REFERENCES [dbo].[BillingPlans] ([Id]);

-- Templates
ALTER TABLE [dbo].[Templates] ADD CONSTRAINT [FK_Templates_TemplateCategoryId_TemplateCategories] 
    FOREIGN KEY([TemplateCategoryId]) REFERENCES [dbo].[TemplateCategories] ([Id]);
ALTER TABLE [dbo].[Templates] ADD CONSTRAINT [FK_Templates_CreatedBy_Users] 
    FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id]);
ALTER TABLE [dbo].[Templates] ADD CONSTRAINT [FK_Templates_BoardId_Boards] 
    FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id]);

-- Exports
ALTER TABLE [dbo].[Exports] ADD CONSTRAINT [FK_Exports_WorkspaceId_Workspaces] 
    FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id]);
ALTER TABLE [dbo].[Exports] ADD CONSTRAINT [FK_Exports_CreatedBy_Users] 
    FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id]);

-- WorkspaceMembershipDomains
ALTER TABLE [dbo].[WorkspaceMembershipDomains] ADD CONSTRAINT [FK_WorkspaceMembershipDomains_WorkspaceId_Workspaces] 
    FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id]);
