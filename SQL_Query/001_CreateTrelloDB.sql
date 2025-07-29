CREATE DATABASE Trello
GO
USE [Trello]
GO

CREATE TABLE [dbo].[Activities](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CreatedAt] [datetime] NULL,
	[ActivityDescription] [text] NULL,
	[UserId] [int] NULL,
	[OwnerTypeId] [int] NULL,
	[OwnerId] [int] NULL,
	CONSTRAINT [PK__Activiti__3214EC072A5742BB] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

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
	CONSTRAINT [PK__Attachme__3214EC0736B035E0] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[BillingContacts](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NULL,
	[WorkspaceId] [int] NULL,
	[BillingContactName] [varchar](50) NULL,
	[BillingContactEmail] [varchar](100) NULL,
	[BillingLanguage] [int] NULL,
	[AdditionalInvoiceDetail] [varchar](250) NULL,
	CONSTRAINT [PK__Billings__3214EC07EFDED48B] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[BillingPlans](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[PlanName] [varchar](100) NULL,
	[BIllingPlanType] [varchar](50) NULL,
	[PricePerUser] [decimal](10, 2) NULL,
	[BillingPlanStatus] [varchar](50) NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[BoardCollections](
	[BoardId] [int] NOT NULL,
	[CollectionId] [int] NOT NULL,
	PRIMARY KEY CLUSTERED ([BoardId] ASC, [CollectionId] ASC)
)
GO

CREATE TABLE [dbo].[BoardPowerUps](
	[BoardId] [int] NOT NULL,
	[PowerUpId] [int] NOT NULL,
	PRIMARY KEY CLUSTERED ([BoardId] ASC, [PowerUpId] ASC)
)
GO

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
	CONSTRAINT [PK__Boards__3214EC071F2FF552] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[BoardUsers](
	[BoardId] [int] NOT NULL,
	[UserID] [int] NOT NULL,
	[AccessedAt] [datetime] NULL,
	PRIMARY KEY CLUSTERED ([BoardId] ASC, [UserID] ASC)
)
GO

CREATE TABLE [dbo].[CardLabels](
	[CardId] [int] NOT NULL,
	[LabelId] [int] NOT NULL,
	PRIMARY KEY CLUSTERED ([CardId] ASC, [LabelId] ASC)
)
GO

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
	CONSTRAINT [PK__Cards__3214EC073D8B1AD9] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[CardStickers](
	[CardId] [int] NOT NULL,
	[StickerId] [int] NOT NULL,
	[PositionX] [float] NULL,
	[PositionY] [float] NULL,
	[IndexZ] [int] NULL,
	PRIMARY KEY CLUSTERED ([CardId] ASC, [StickerId] ASC)
)
GO

CREATE TABLE [dbo].[CheckListItems](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CheckListItemName] [varchar](50) NULL,
	[MemberId] [int] NULL,
	[CheckListId] [int] NULL,
	[DueDate] [date] NULL,
	[CheckListItemStatus] [bit] NULL,
	CONSTRAINT [PK__CheckLis__3214EC071563C8B8] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[CheckLists](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CheckListName] [varchar](50) NULL,
	[CardId] [int] NULL,
	CONSTRAINT [PK__CheckLis__3214EC077AD86A3B] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[Collections](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CollectionName] [varchar](50) NULL,
	[CreatedBy] [int] NULL,
	[CreatedAt] [datetime] NULL,
	[WorkspaceId] [int] NULL,
	CONSTRAINT [PK__Collecti__3214EC0734B4CC80] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[Colors](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ColorName] [text] NULL,
	[Icon] [text] NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[CommentReactions](
	[CommentId] [int] NOT NULL,
	[ReactionId] [int] NOT NULL,
	[CreatedBy] [int] NULL,
	PRIMARY KEY CLUSTERED ([CommentId] ASC, [ReactionId] ASC)
)
GO

CREATE TABLE [dbo].[Comments](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Content] [text] NULL,
	[CardId] [int] NULL,
	[CreatedAt] [datetime] NULL,
	[CreatedBy] [int] NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[CustomFields](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Title] [varchar](50) NULL,
	[FieldType] [varchar](50) NULL,
	[BoardId] [int] NULL,
	[Position] [int] NULL,
	CONSTRAINT [PK__CustomFi__3214EC073CD980A0] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[Exports](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[WorkspaceId] [int] NULL,
	[CreatedBy] [int] NULL,
	[CreatedAt] [datetime] NULL,
	[Size] [int] NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[FieldItems](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ColorId] [int] NULL,
	[FieldItemValue] [varchar](50) NULL,
	[Priority] [int] NULL,
	[CustomFieldId] [int] NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[FieldValues](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CardId] [int] NULL,
	[FieldValue] [varchar](50) NULL,
	[CustomFieldId] [int] NULL,
	CONSTRAINT [PK__FieldVal__3214EC07AEF309A2] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[Labels](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Title] [varchar](100) NULL,
	[ColorId] [int] NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[MemberReactions](
	[MemberId] [int] NOT NULL,
	[ReactionId] [int] NOT NULL,
	PRIMARY KEY CLUSTERED ([MemberId] ASC, [ReactionId] ASC)
)
GO

CREATE TABLE [dbo].[Members](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NULL,
	[PermissionId] [int] NULL,
	[OwnerTypeId] [int] NULL,
	[OwnerId] [int] NULL,
	[InvitedBy] [int] NULL,
	[JoinedAt] [datetime] NULL,
	[Status] [varchar](50) NULL,
	CONSTRAINT [PK__Members__3214EC073DC2BFFB] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[Notifications](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ActivityId] [int] NULL,
	[NotificationStatus] [varchar](50) NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[OwnerTypes](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[OwnerTypeValue] [varchar](50) NOT NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[PaymentInformations](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[BillingContactId] [int] NULL,
	[CardNumber] [varchar](20) NULL,
	[CardBrand] [varchar](50) NULL,
	[ExpirationDate] [varchar](20) NULL,
	[Cvv] [varchar](10) NULL,
	[Country] [varchar](100) NULL,
	[PostalCode] [varchar](20) NULL,
	CONSTRAINT [PK__PaymentI__3214EC074F007E1C] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[PowerUpCategories](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CategoryName] [varchar](50) NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

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
	CONSTRAINT [PK__PowerUps__3214EC078DB44AF1] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[Reactions](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Icon] [varchar](50) NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[RolePermissions](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[PermissionName] [varchar](50) NULL,
	[PermissionCode] [varchar](50) NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[SettingKeys](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[KeyName] [varchar](100) NULL,
	[Description] [text] NULL,
	[OwnerTypeId] [int] NULL,
	[DefaultValue] [int] NULL,
	[TypeValue] [varchar](50) NULL,
	CONSTRAINT [PK__SettingK__3214EC077990E501] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[SettingKeySettingOptions](
	[SettingKeyId] [int] NOT NULL,
	[SettingOptionId] [int] NOT NULL,
	PRIMARY KEY CLUSTERED ([SettingKeyId] ASC, [SettingOptionId] ASC)
)
GO

CREATE TABLE [dbo].[SettingOptions](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[DisplayValue] [varchar](255) NULL,
	[SettingOptionValue] [varchar](50) NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[SettingValues](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[SettingKeyId] [int] NULL,
	[SettingValue] [int] NULL,
	[OwnerId] [int] NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[ShareLinks](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[OwnerTypeId] [int] NULL,
	[OwnerId] [int] NULL,
	[PermissionId] [int] NULL,
	[ShareLinkToken] [varchar](255) NULL,
	[ShareLinkStatus] [bit] NULL,
	CONSTRAINT [PK__ShareLin__3214EC07F0BCEE40] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

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
)
GO

CREATE TABLE [dbo].[Stickers](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[StickerName] [varchar](50) NULL,
	[StickerUrl] [varchar](255) NULL,
	CONSTRAINT [PK__Stickers__3214EC079F7D709C] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

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
)
GO

CREATE TABLE [dbo].[TemplateCategories](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CategoryName] [varchar](50) NULL,
	[IconUrl] [varchar](max) NULL,
	CONSTRAINT [PK__Template__3214EC07A205E643] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

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
)
GO

CREATE TABLE [dbo].[Users](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](255) NULL,
	[Bio] [text] NULL,
	[Email] [varchar](255) NULL,
	[LastActive] [datetime] NULL,
	[CreatedAt] [datetime] NULL,
	[PictureUrl] [varchar](max) NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

CREATE TABLE [dbo].[WorkspaceMembershipDomains](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[WorkspaceId] [int] NOT NULL,
	[EmailDomain] [text] NOT NULL,
	[CreatedAt] [datetime] NULL,
	PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

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
	CONSTRAINT [PK__Workspac__3214EC07E07B14B6] PRIMARY KEY CLUSTERED ([Id] ASC)
)
GO

-- FOREIGN KEY CONSTRAINTS
ALTER TABLE [dbo].[Activities] ADD CONSTRAINT [FK__Activitie__UserI__08B54D69] FOREIGN KEY([UserId]) REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Activities] ADD CONSTRAINT [FK_Activities_OwnerTypes] FOREIGN KEY([OwnerTypeId]) REFERENCES [dbo].[OwnerTypes] ([Id])
GO

ALTER TABLE [dbo].[Attachments] ADD CONSTRAINT [FK__Attachmen__CardI__1AD3FDA4] FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id])
GO
ALTER TABLE [dbo].[Attachments] ADD CONSTRAINT [FK__Attachmen__Uploa__1BC821DD] FOREIGN KEY([UploadBy]) REFERENCES [dbo].[Users] ([Id])
GO

ALTER TABLE [dbo].[BillingContacts] ADD CONSTRAINT [FK__Billings__Langua__282DF8C2] FOREIGN KEY([BillingLanguage]) REFERENCES [dbo].[SettingOptions] ([Id])
GO
ALTER TABLE [dbo].[BillingContacts] ADD CONSTRAINT [FK__Billings__UserId__2645B050] FOREIGN KEY([UserId]) REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[BillingContacts] ADD CONSTRAINT [FK__Billings__Worksp__2739D489] FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id])
GO

ALTER TABLE [dbo].[BoardCollections] ADD CONSTRAINT [FK__BoardColl__Board__30C33EC3] FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id])
GO
ALTER TABLE [dbo].[BoardCollections] ADD CONSTRAINT [FK__BoardColl__Colle__31B762FC] FOREIGN KEY([CollectionId]) REFERENCES [dbo].[Collections] ([Id])
GO

ALTER TABLE [dbo].[BoardPowerUps] ADD CONSTRAINT [FK__BoardPowe__Board__367C1819] FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id])
GO
ALTER TABLE [dbo].[BoardPowerUps] ADD CONSTRAINT [FK__BoardPowe__Power__37703C52] FOREIGN KEY([PowerUpId]) REFERENCES [dbo].[PowerUps] ([Id])
GO

ALTER TABLE [dbo].[Boards] ADD CONSTRAINT [FK__Boards__CreatedB__0C85DE4D] FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Boards] ADD CONSTRAINT [FK__Boards__Workspac__0D7A0286] FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id])
GO
ALTER TABLE [dbo].[Boards] ADD CONSTRAINT [FK_Boards_UpdatedBy_Users] FOREIGN KEY([UpdatedBy]) REFERENCES [dbo].[Users] ([Id])
GO

ALTER TABLE [dbo].[BoardUsers] ADD CONSTRAINT [FK__BoardUser__Board__55009F39] FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id]) ON DELETE CASCADE
GO
ALTER TABLE [dbo].[BoardUsers] ADD FOREIGN KEY([UserID]) REFERENCES [dbo].[Users] ([Id]) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[CardLabels] ADD CONSTRAINT [FK__CardLabel__CardI__2CF2ADDF] FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id])
GO
ALTER TABLE [dbo].[CardLabels] ADD FOREIGN KEY([LabelId]) REFERENCES [dbo].[Labels] ([Id])
GO

ALTER TABLE [dbo].[Cards] ADD CONSTRAINT [FK__Cards__StageId__114A936A] FOREIGN KEY([StageId]) REFERENCES [dbo].[Stages] ([Id])
GO
ALTER TABLE [dbo].[Cards] ADD CONSTRAINT [FK_Cards_UpdatedBy_Users] FOREIGN KEY([UpdatedBy]) REFERENCES [dbo].[Users] ([Id])
GO

ALTER TABLE [dbo].[CardStickers] ADD CONSTRAINT [FK__CardStick__CardI__3A4CA8FD] FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id])
GO
ALTER TABLE [dbo].[CardStickers] ADD CONSTRAINT [FK__CardStick__Stick__3B40CD36] FOREIGN KEY([StickerId]) REFERENCES [dbo].[Stickers] ([Id])
GO

ALTER TABLE [dbo].[CheckListItems] ADD CONSTRAINT [FK__CheckList__Check__151B244E] FOREIGN KEY([CheckListId]) REFERENCES [dbo].[CheckLists] ([Id])
GO
ALTER TABLE [dbo].[CheckListItems] ADD CONSTRAINT [FK__CheckList__Membe__14270015] FOREIGN KEY([MemberId]) REFERENCES [dbo].[Members] ([Id])
GO

ALTER TABLE [dbo].[CheckLists] ADD CONSTRAINT [FK__CheckList__CardI__1332DBDC] FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id])
GO

ALTER TABLE [dbo].[Collections] ADD CONSTRAINT [FK__Collectio__Creat__0E6E26BF] FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Collections] ADD CONSTRAINT [FK_Collections_Workspaces] FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id])
GO

ALTER TABLE [dbo].[CommentReactions] ADD FOREIGN KEY([CommentId]) REFERENCES [dbo].[Comments] ([Id])
GO
ALTER TABLE [dbo].[CommentReactions] ADD FOREIGN KEY([ReactionId]) REFERENCES [dbo].[Reactions] ([Id])
GO
ALTER TABLE [dbo].[CommentReactions] ADD CONSTRAINT [FK_CommentReactions_Member_CreatedBy] FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Members] ([Id])
GO

ALTER TABLE [dbo].[Comments] ADD CONSTRAINT [FK__Comments__CardId__1CBC4616] FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id])
GO
ALTER TABLE [dbo].[Comments] ADD FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id])
GO

ALTER TABLE [dbo].[CustomFields] ADD CONSTRAINT [FK__CustomFie__Board__160F4887] FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id])
GO

ALTER TABLE [dbo].[Exports] ADD FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Exports] ADD CONSTRAINT [FK__Exports__Workspa__245D67DE] FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id])
GO

ALTER TABLE [dbo].[FieldItems] ADD FOREIGN KEY([ColorId]) REFERENCES [dbo].[Colors] ([Id])
GO
ALTER TABLE [dbo].[FieldItems] ADD CONSTRAINT [FK__FieldItem__Custo__17F790F9] FOREIGN KEY([CustomFieldId]) REFERENCES [dbo].[CustomFields] ([Id])
GO

ALTER TABLE [dbo].[FieldValues] ADD CONSTRAINT [FK__FieldValu__CardI__18EBB532] FOREIGN KEY([CardId]) REFERENCES [dbo].[Cards] ([Id])
GO
ALTER TABLE [dbo].[FieldValues] ADD CONSTRAINT [FK__FieldValu__Custo__19DFD96B] FOREIGN KEY([CustomFieldId]) REFERENCES [dbo].[CustomFields] ([Id])
GO

ALTER TABLE [dbo].[Labels] ADD FOREIGN KEY([ColorId]) REFERENCES [dbo].[Colors] ([Id])
GO

ALTER TABLE [dbo].[MemberReactions] ADD CONSTRAINT [FK__MemberRea__Membe__2EDAF651] FOREIGN KEY([MemberId]) REFERENCES [dbo].[Members] ([Id])
GO
ALTER TABLE [dbo].[MemberReactions] ADD FOREIGN KEY([ReactionId]) REFERENCES [dbo].[Reactions] ([Id])
GO

ALTER TABLE [dbo].[Members] ADD CONSTRAINT [FK__Members__Permiss__1F98B2C1] FOREIGN KEY([PermissionId]) REFERENCES [dbo].[RolePermissions] ([Id])
GO
ALTER TABLE [dbo].[Members] ADD CONSTRAINT [FK__Members__UserId__1EA48E88] FOREIGN KEY([UserId]) REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Members] ADD CONSTRAINT [FK_Members_OwnerTypes] FOREIGN KEY([OwnerTypeId]) REFERENCES [dbo].[OwnerTypes] ([Id])
GO

ALTER TABLE [dbo].[Notifications] ADD CONSTRAINT [FK__Notificat__Activ__09A971A2] FOREIGN KEY([ActivityId]) REFERENCES [dbo].[Activities] ([Id])
GO

ALTER TABLE [dbo].[PaymentInformations] ADD CONSTRAINT [FK__PaymentIn__Billi__29221CFB] FOREIGN KEY([BillingContactId]) REFERENCES [dbo].[BillingContacts] ([Id])
GO

ALTER TABLE [dbo].[PowerUps] ADD CONSTRAINT [FK__PowerUps__PowerU__2BFE89A6] FOREIGN KEY([PowerUpCategoryId]) REFERENCES [dbo].[PowerUpCategories] ([Id])
GO

ALTER TABLE [dbo].[SettingKeys] ADD CONSTRAINT [FK_SettingKeys_OwnerTypes] FOREIGN KEY([OwnerTypeId]) REFERENCES [dbo].[OwnerTypes] ([Id])
GO

ALTER TABLE [dbo].[SettingKeySettingOptions] ADD CONSTRAINT [FK__SettingKe__Setti__32AB8735] FOREIGN KEY([SettingKeyId]) REFERENCES [dbo].[SettingKeys] ([Id])
GO
ALTER TABLE [dbo].[SettingKeySettingOptions] ADD FOREIGN KEY([SettingOptionId]) REFERENCES [dbo].[SettingOptions] ([Id])
GO

ALTER TABLE [dbo].[SettingValues] ADD CONSTRAINT [FK__SettingVa__Setti__07C12930] FOREIGN KEY([SettingKeyId]) REFERENCES [dbo].[SettingKeys] ([Id])
GO

ALTER TABLE [dbo].[ShareLinks] ADD CONSTRAINT [FK__ShareLink__Permi__0B91BA14] FOREIGN KEY([PermissionId]) REFERENCES [dbo].[RolePermissions] ([Id])
GO
ALTER TABLE [dbo].[ShareLinks] ADD CONSTRAINT [FK_ShareLinks_OwnerTypes] FOREIGN KEY([OwnerTypeId]) REFERENCES [dbo].[OwnerTypes] ([Id])
GO

ALTER TABLE [dbo].[Stages] ADD CONSTRAINT [FK__Stages__BoardId__0F624AF8] FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id])
GO
ALTER TABLE [dbo].[Stages] ADD FOREIGN KEY([ColorId]) REFERENCES [dbo].[Colors] ([Id])
GO
ALTER TABLE [dbo].[Stages] ADD CONSTRAINT [FK_Stages_UpdatedBy_Users] FOREIGN KEY([UpdatedBy]) REFERENCES [dbo].[Users] ([Id])
GO

ALTER TABLE [dbo].[Subscriptions] ADD CONSTRAINT [FK__Subscript__Billi__2A164134] FOREIGN KEY([BillingContactId]) REFERENCES [dbo].[BillingContacts] ([Id])
GO
ALTER TABLE [dbo].[Subscriptions] ADD FOREIGN KEY([BillingPlanId]) REFERENCES [dbo].[BillingPlans] ([Id])
GO

ALTER TABLE [dbo].[Templates] ADD CONSTRAINT [FK__Templates__Board__22751F6C] FOREIGN KEY([BoardId]) REFERENCES [dbo].[Boards] ([Id])
GO
ALTER TABLE [dbo].[Templates] ADD FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Templates] ADD CONSTRAINT [FK__Templates__Templ__208CD6FA] FOREIGN KEY([TemplateCategoryId]) REFERENCES [dbo].[TemplateCategories] ([Id])
GO

ALTER TABLE [dbo].[WorkspaceMembershipDomains] ADD CONSTRAINT [FK__Workspace__Works__236943A5] FOREIGN KEY([WorkspaceId]) REFERENCES [dbo].[Workspaces] ([Id])
GO

ALTER TABLE [dbo].[Workspaces] ADD CONSTRAINT [FK__Workspace__Creat__0A9D95DB] FOREIGN KEY([CreatedBy]) REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Workspaces] ADD CONSTRAINT [FK_Workspaces_UpdatedBy_Users] FOREIGN KEY([UpdatedBy]) REFERENCES [dbo].[Users] ([Id])
GO
