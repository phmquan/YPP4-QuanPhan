-- Users
INSERT INTO Users (Username, Bio, Email, LastActive, CreatedAt, UpdatedAt, Avatar, FullName)
VALUES
('quang', 'Loves coding', 'quang@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'pic1.jpg', 'Quang Nguyen'),
('minh', 'Gym and travel', 'minh@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'pic2.jpg', 'Minh Tran'),
('hoa', 'Food blogger', 'hoa@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'pic3.jpg', 'Hoa Le');

-- WorkspaceType
INSERT INTO WorkspaceType (TypeName) VALUES
('Personal'),
('Team'),
('Organization');
INSERT INTO RolePermission (PermissionName, PermissionCode) VALUES
('Admin', 'ADMIN'),   -- Id = 1
('Member', 'MEMBER'), -- Id = 2
('Viewer', 'VIEWER'); -- Id = 3
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
INSERT INTO OwnerType (OwnerTypeValue) VALUES
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

-- workspace (Id=1) → OwnerId: 1, 2, 3
INSERT INTO Members (UserId, RolePermissionId, OwnerTypeId, OwnerId, InvitedBy, JoinedAt, MemberStatus) VALUES
(1, 1, 1, 1, 2, CURRENT_TIMESTAMP, 'active'),
(1, 2, 1, 2, 1, CURRENT_TIMESTAMP, 'active'),
(1, 3, 1, 3, 1, CURRENT_TIMESTAMP, 'pending');

-- board (Id=2) → OwnerId: 1, 2, 3
INSERT INTO Members (UserId, RolePermissionId, OwnerTypeId, OwnerId, InvitedBy, JoinedAt, MemberStatus) VALUES
(1, 1, 2, 1, 1, CURRENT_TIMESTAMP, 'active'),
(1, 2, 2, 2, 2, CURRENT_TIMESTAMP, 'active'),
(1, 3, 2, 3, 3, CURRENT_TIMESTAMP, 'pending');

-- user (Id=3) → OwnerId: 1, 2, 3
INSERT INTO Members (UserId, RolePermissionId, OwnerTypeId, OwnerId, InvitedBy, JoinedAt, MemberStatus) VALUES
(1, 1, 3, 1, 2, CURRENT_TIMESTAMP, 'active'),
(1, 2, 3, 2, 3, CURRENT_TIMESTAMP, 'active'),
(1, 3, 3, 3, 1, CURRENT_TIMESTAMP, 'pending');

INSERT INTO TemplateCategory (CategoryValue, DisplayValue, IconUrl) VALUES
('marketing', 'Marketing', 'https://example.com/icons/marketing.png'),
('design', 'Design', 'https://example.com/icons/design.png'),
('finance', 'Finance', 'https://example.com/icons/finance.png');

INSERT INTO Template
(Title, TemplateDescription, CategoryId, Viewed, Copied, CreatedBy, CreatedAt, UpdatedAt, UpdatedBy, BoardId, BackgroundUrl)
VALUES
('Marketing Plan', 'A basic marketing plan template', 1, 120, 45, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'https://example.com/bg/marketing.jpg'),
('Design Mockup', 'Template for design mockups', 2, 80, 30, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'https://example.com/bg/design.jpg'),
('Financial Report', 'Monthly financial report template', 3, 60, 20, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'https://example.com/bg/finance.jpg');

-- Color data
INSERT INTO Color (ColorName, ColorHex, Icon) VALUES
('Red', '#FF0000', 'red-icon.svg'),
('Blue', '#0000FF', 'blue-icon.svg'),
('Green', '#00FF00', 'green-icon.svg'),
('Yellow', '#FFFF00', 'yellow-icon.svg'),
('Purple', '#800080', 'purple-icon.svg'),
('Orange', '#FFA500', 'orange-icon.svg');

-- CardCoverType data
INSERT INTO CardCoverType (TypeName, TypeValue) VALUES
('Color', 'solid-color'),
('Image', 'background-image'),
('Gradient', 'gradient-color'),
('Pattern', 'pattern-background');

-- AttachmentType data
INSERT INTO AttachmentType (TypeName, TypeExtension) VALUES
('Image', '.jpg'),
('Image', '.png'),
('Image', '.gif'),
('Document', '.pdf'),
('Document', '.docx'),
('Document', '.xlsx'),
('Video', '.mp4'),
('Audio', '.mp3'),
('Archive', '.zip'),
('Text', '.txt');

-- Stage data
INSERT INTO Stage (Title, CreatedAt, CreatedBy, BoardId, StageStatus, ColorId, Position, UpdatedAt, UpdatedBy) VALUES
('To Do', CURRENT_TIMESTAMP, 1, 1, 'ACTIVE', 1, 1, CURRENT_TIMESTAMP, 1),
('In Progress', CURRENT_TIMESTAMP, 1, 1, 'ACTIVE', 2, 2, CURRENT_TIMESTAMP, 1),
('Review', CURRENT_TIMESTAMP, 1, 1, 'ACTIVE', 3, 3, CURRENT_TIMESTAMP, 1),
('Done', CURRENT_TIMESTAMP, 1, 1, 'ACTIVE', 4, 4, CURRENT_TIMESTAMP, 1),
('Backlog', CURRENT_TIMESTAMP, 2, 2, 'ACTIVE', 5, 1, CURRENT_TIMESTAMP, 2),
('Development', CURRENT_TIMESTAMP, 2, 2, 'ACTIVE', 6, 2, CURRENT_TIMESTAMP, 2),
('Testing', CURRENT_TIMESTAMP, 2, 2, 'ACTIVE', 1, 3, CURRENT_TIMESTAMP, 2),
('Deployment', CURRENT_TIMESTAMP, 2, 2, 'ACTIVE', 2, 4, CURRENT_TIMESTAMP, 2),
('Planning', CURRENT_TIMESTAMP, 3, 3, 'ACTIVE', 3, 1, CURRENT_TIMESTAMP, 3),
('Archived', CURRENT_TIMESTAMP, 3, 3, 'ARCHIVE', 4, 2, CURRENT_TIMESTAMP, 3);

-- Cards data
INSERT INTO Cards (StageId, Title, CardDescription, CreatedAt, CreatedBy, CardStatus, CardLocation, StartDate, DueDate, CardCoverTypeId, CoverValue, Position, UpdatedAt, UpdatedBy, IsTemplate, IsCompleted) VALUES
(1, 'Setup Project Environment', 'Configure development environment and install dependencies', CURRENT_TIMESTAMP, 1, 'ACTIVE', 'Home Office', '2025-08-10', '2025-08-15', 1, '#FF6B6B', 1, CURRENT_TIMESTAMP, 1, 0, 0),
(1, 'Design Database Schema', 'Create ERD and design database tables', CURRENT_TIMESTAMP, 1, 'ACTIVE', 'Office', '2025-08-12', '2025-08-18', 2, 'https://example.com/design-bg.jpg', 2, CURRENT_TIMESTAMP, 1, 0, 0),
(2, 'Implement User Authentication', 'Build login and registration functionality', CURRENT_TIMESTAMP, 2, 'ACTIVE', 'Remote', '2025-08-14', '2025-08-20', 1, '#4ECDC4', 1, CURRENT_TIMESTAMP, 2, 0, 0),
(2, 'Create API Endpoints', 'Develop REST API for core features', CURRENT_TIMESTAMP, 1, 'ACTIVE', 'Office', '2025-08-15', '2025-08-25', 3, 'linear-gradient(45deg, #667eea 0%, #764ba2 100%)', 2, CURRENT_TIMESTAMP, 1, 0, 0),
(3, 'Code Review - Authentication', 'Review authentication implementation', CURRENT_TIMESTAMP, 3, 'ACTIVE', 'Office', '2025-08-18', '2025-08-20', 1, '#45B7D1', 1, CURRENT_TIMESTAMP, 3, 0, 0),
(4, 'Project Kickoff Meeting', 'Initial project planning and team alignment', CURRENT_TIMESTAMP, 1, 'COMPLETED', 'Conference Room', '2025-08-01', '2025-08-02', 1, '#96CEB4', 1, CURRENT_TIMESTAMP, 1, 0, 1),
(5, 'Research New Framework', 'Evaluate potential frameworks for the project', CURRENT_TIMESTAMP, 2, 'ACTIVE', 'Library', '2025-08-16', '2025-08-22', 2, 'https://example.com/research-bg.jpg', 1, CURRENT_TIMESTAMP, 2, 0, 0),
(6, 'Frontend Development', 'Build user interface components', CURRENT_TIMESTAMP, 2, 'ACTIVE', 'Home Office', '2025-08-20', '2025-08-30', 1, '#FFEAA7', 2, CURRENT_TIMESTAMP, 2, 0, 0),
(7, 'Unit Testing', 'Write and execute unit tests', CURRENT_TIMESTAMP, 3, 'ACTIVE', 'Office', '2025-08-25', '2025-08-28', 1, '#DDA0DD', 1, CURRENT_TIMESTAMP, 3, 0, 0),
(8, 'Production Deployment', 'Deploy application to production environment', CURRENT_TIMESTAMP, 1, 'ACTIVE', 'Data Center', '2025-08-30', '2025-09-01', 1, '#FF7675', 1, CURRENT_TIMESTAMP, 1, 0, 0);

-- Attachment data
INSERT INTO Attachment (CardId, AttachmentTypeId, AttachmentPath, AttachmentName, CreatedAt, CreatedBy, Size, IsCover, Thumbnail) VALUES
(1, 1, '/uploads/project-setup.jpg', 'Project Setup Guide.jpg', CURRENT_TIMESTAMP, 1, '2.5MB', 1, '/thumbnails/project-setup-thumb.jpg'),
(1, 4, '/uploads/requirements.pdf', 'Project Requirements.pdf', CURRENT_TIMESTAMP, 1, '1.2MB', 0, '/thumbnails/pdf-thumb.jpg'),
(2, 1, '/uploads/database-erd.png', 'Database ERD.png', CURRENT_TIMESTAMP, 1, '850KB', 1, '/thumbnails/erd-thumb.png'),
(2, 5, '/uploads/schema-design.docx', 'Schema Design Document.docx', CURRENT_TIMESTAMP, 1, '3.1MB', 0, '/thumbnails/doc-thumb.jpg'),
(3, 2, '/uploads/auth-flow.png', 'Authentication Flow.png', CURRENT_TIMESTAMP, 2, '1.1MB', 1, '/thumbnails/auth-flow-thumb.png'),
(3, 10, '/uploads/auth-notes.txt', 'Authentication Notes.txt', CURRENT_TIMESTAMP, 2, '15KB', 0, '/thumbnails/txt-thumb.jpg'),
(4, 4, '/uploads/api-documentation.pdf', 'API Documentation.pdf', CURRENT_TIMESTAMP, 1, '2.8MB', 0, '/thumbnails/api-doc-thumb.jpg'),
(4, 6, '/uploads/api-spec.xlsx', 'API Specification.xlsx', CURRENT_TIMESTAMP, 1, '890KB', 0, '/thumbnails/excel-thumb.jpg'),
(5, 1, '/uploads/code-review.jpg', 'Code Review Checklist.jpg', CURRENT_TIMESTAMP, 3, '1.5MB', 1, '/thumbnails/review-thumb.jpg'),
(6, 7, '/uploads/meeting-recording.mp4', 'Kickoff Meeting Recording.mp4', CURRENT_TIMESTAMP, 1, '125MB', 0, '/thumbnails/video-thumb.jpg'),
(6, 8, '/uploads/meeting-audio.mp3', 'Meeting Audio Summary.mp3', CURRENT_TIMESTAMP, 1, '25MB', 0, '/thumbnails/audio-thumb.jpg'),
(7, 9, '/uploads/research-materials.zip', 'Framework Research Materials.zip', CURRENT_TIMESTAMP, 2, '45MB', 0, '/thumbnails/zip-thumb.jpg'),
(8, 2, '/uploads/ui-mockup.png', 'UI Mockup Design.png', CURRENT_TIMESTAMP, 2, '3.2MB', 1, '/thumbnails/ui-mockup-thumb.png'),
(9, 4, '/uploads/test-report.pdf', 'Unit Test Report.pdf', CURRENT_TIMESTAMP, 3, '1.8MB', 0, '/thumbnails/test-report-thumb.jpg'),
(10, 5, '/uploads/deployment-guide.docx', 'Deployment Guide.docx', CURRENT_TIMESTAMP, 1, '2.1MB', 0, '/thumbnails/deploy-guide-thumb.jpg');

-- SettingKey data
INSERT INTO SettingKey (KeyName, SettingKeyDescription, OwnerTypeId, DefaultValue, IsBoolean) VALUES
('board_notifications', 'Enable notifications for board activities', 2, 1, 1),
('workspace_public_visibility', 'Make workspace publicly visible', 1, 0, 1),
('user_email_notifications', 'Enable email notifications for user', 3, 1, 1),
('board_due_date_reminders', 'Send reminders for card due dates', 2, 1, 1),
('workspace_member_limit', 'Maximum number of members allowed in workspace', 1, 50, 0),
('user_theme_preference', 'User interface theme preference (0=light, 1=dark)', 3, 0, 0),
('board_auto_archive', 'Automatically archive completed cards', 2, 0, 1),
('workspace_backup_frequency', 'Backup frequency in days', 1, 7, 0),
('visibility', 'Board visibility settings (0=private, 1=team, 2=public)', 2, 1, 0),
('visibility', 'Workspace visibility settings (0=private, 1=organization, 2=public)', 1, 0, 0);

-- SettingValue data
INSERT INTO SettingValue (SettingKeyId, SettingContent, CreatedAt, CreatedBy, UpdatedAt, UpdatedBy, OwnerId) VALUES
-- Board settings (OwnerId refers to Board.Id)
(1, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 1), -- board_notifications for board 1
(4, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 1), -- board_due_date_reminders for board 1
(9, 14, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 1), -- board_visibility for board 1 (team visible - option 14)
(7, 0, CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, 2, 2), -- board_auto_archive for board 2
(9, 15, CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, 2, 2), -- board_visibility for board 2 (public - option 15)
(1, 0, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 3, 3), -- board_notifications for board 3
(9, 13, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 3, 3), -- board_visibility for board 3 (private - option 13)

-- Workspace settings (OwnerId refers to Workspace.Id)
(2, 0, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 1), -- workspace_public_visibility for workspace 1
(10, 16, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 1), -- workspace_visibility for workspace 1 (private - option 16)
(5, 2, CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, 2, 2), -- workspace_member_limit for workspace 2 (25 members - option 2)
(10, 17, CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, 2, 2), -- workspace_visibility for workspace 2 (organization - option 17)
(8, 10, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 3, 3), -- workspace_backup_frequency for workspace 3 (every 3 days - option 10)
(10, 18, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 3, 3), -- workspace_visibility for workspace 3 (public - option 18)

-- User settings (OwnerId refers to Users.Id)
(3, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 1), -- user_email_notifications for user 1
(6, 7, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1, 1), -- user_theme_preference for user 1 (dark theme - option 7)
(3, 0, CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, 2, 2), -- user_email_notifications for user 2
(6, 6, CURRENT_TIMESTAMP, 2, CURRENT_TIMESTAMP, 2, 2), -- user_theme_preference for user 2 (light theme - option 6)
(3, 1, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 3, 3), -- user_email_notifications for user 3
(6, 6, CURRENT_TIMESTAMP, 3, CURRENT_TIMESTAMP, 3, 3); -- user_theme_preference for user 3 (light theme - option 6)

-- SettingOption data (for non-boolean setting keys)
INSERT INTO SettingOption (DisplayValue, SettingOptionValue) VALUES
-- Options for workspace_member_limit (SettingKey Id=5)
('10 Members', '10'),
('25 Members', '25'),
('50 Members', '50'),
('100 Members', '100'),
('Unlimited', '999'),

-- Options for user_theme_preference (SettingKey Id=6)
('Light Theme', '0'),
('Dark Theme', '1'),
('Auto (System)', '2'),

-- Options for workspace_backup_frequency (SettingKey Id=8)
('Daily', '1'),
('Every 3 Days', '3'),
('Weekly', '7'),
('Monthly', '30'),

-- Options for board_visibility (SettingKey Id=9)
('Private', '0'),
('Team Visible', '1'),
('Public', '2'),

-- Options for workspace_visibility (SettingKey Id=10)
('Private', '0'),
('Organization Visible', '1'),
('Public', '2');

-- SettingKeySettingOption junction data (linking non-boolean setting keys to their options)
INSERT INTO SettingKeySettingOption (SettingKeyId, SettingOptionId) VALUES
-- workspace_member_limit options (SettingKey Id=5)
(5, 1), (5, 2), (5, 3), (5, 4), (5, 5),

-- user_theme_preference options (SettingKey Id=6)
(6, 6), (6, 7), (6, 8),

-- workspace_backup_frequency options (SettingKey Id=8)
(8, 9), (8, 10), (8, 11), (8, 12),

-- board_visibility options (SettingKey Id=9)
(9, 13), (9, 14), (9, 15),

-- workspace_visibility options (SettingKey Id=10)
(10, 16), (10, 17), (10, 18);
