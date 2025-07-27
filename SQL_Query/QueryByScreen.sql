-- =============================================================================
-- BOARD MANAGEMENT SYSTEM - SQL QUERIES
-- =============================================================================

-- -----------------------------------------------------------------------------
-- SCREEN 1: TAB BOARDS (SLIDE 4)
-- -----------------------------------------------------------------------------

-- 1. Query 4 suggested templates by Template Category
SELECT TOP 4 
    t.Title, 
    t.BackgroundUrl
FROM Templates t
    JOIN TemplateCategories tc ON tc.Id = t.TemplateCategoryId
WHERE tc.Name = 'Geologist II'; -- ':templateCategory'

-- 2. Query 4 recently accessed Boards by user
SELECT TOP 4 
    b.Name,
    b.BackgroundUrl
FROM Boards b
    JOIN BoardUsers bu ON bu.BoardId = b.Id
    JOIN Users u ON u.Id = bu.UserID
WHERE u.Id = 2 -- userId
ORDER BY bu.AccessedAt DESC;

-- 3. Query all Workspaces where User is a Member
SELECT w.Name
FROM Workspaces w
    JOIN Members m ON m.OwnerId = w.Id
    JOIN OwnerTypes ot ON m.OwnerTypeId = ot.Id
WHERE ot.Value = 'Workspace' 
    AND m.UserId = 1;

-- 4. Query all Workspaces where User is a Member. For each workspace, get all Boards where User is also a Member
SELECT 
    w.Id AS WorkspaceId,
    w.Name AS WorkspaceName,
    b.Id AS BoardId,
    b.Name AS BoardName,
    b.Description,
    b.BackgroundUrl,
    b.CreatedAt
FROM Workspaces w
    -- Find Workspaces where User is a Member
    JOIN Members mw ON mw.OwnerId = w.Id
    JOIN OwnerTypes otw ON otw.Id = mw.OwnerTypeId 
        AND otw.Value = 'WORKSPACE'
    -- Find Boards corresponding to Workspace where User is also a Member
    JOIN Boards b ON b.WorkspaceId = w.Id
    JOIN Members mb ON mb.Id = b.Id
    JOIN OwnerTypes otb ON otb.Id = mb.OwnerTypeId 
        AND otb.Value = 'BOARD'
WHERE mw.UserId = 1
    AND mb.UserId = 1;

-- 5. Query all closed boards where user is a member
SELECT 
    b.Name, 
    w.Name
FROM Boards b
    JOIN Workspaces w ON w.Id = b.WorkspaceId
    JOIN Members m ON m.OwnerId = b.Id
    JOIN OwnerTypes ot ON m.OwnerTypeId = ot.Id
WHERE ot.Value = 'BOARD'
    AND m.UserId = 3
    AND b.Status = 'CLOSED';

-- -----------------------------------------------------------------------------
-- SCREEN 2: TEMPLATES TAB (SLIDE 5)
-- -----------------------------------------------------------------------------

-- 6. Get top 10 template categories
SELECT TOP 10 
    Name,
    IconUrl
FROM TemplateCategories;

-- 7. Get New and notable templates
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

-- -----------------------------------------------------------------------------
-- SCREEN 3: TEMPLATE DETAIL (SLIDE 6)
-- -----------------------------------------------------------------------------

-- 8. Get template details and the Board associated with that template
SELECT 
    t.Title, 
    u.Username,
    t.Copied,
    t.Viewed,
    t.Description,
    b.Name,
    b.Status
FROM Templates t
    JOIN Boards b ON b.Id = t.BoardId
    JOIN Users u ON t.CreatedBy = u.Id
WHERE t.Id = 1; -- templateId

-- -----------------------------------------------------------------------------
-- SCREEN 4: CREATE WORKSPACE (SLIDE 7)
-- -----------------------------------------------------------------------------

-- 9. Insert data into Workspaces
INSERT INTO Workspaces (Name, Description, Type) 
VALUES ('', '', '');

-- -----------------------------------------------------------------------------
-- SCREEN 5: TAB BOARDS IN WORKSPACE WITH USERID = 1 (SLIDE 8)
-- -----------------------------------------------------------------------------

-- 10. Get Workspace Name, SettingKey='visibility' and SettingValue related to SettingKeys of Workspace
WITH SettingValueForWorkspace AS (
    SELECT 
        sv.OwnerId,
        sk.KeyName,
        so.Value
    FROM SettingValues sv
        JOIN SettingKeys sk ON sk.Id = sv.SettingKeyId
        JOIN SettingOptions so ON sv.Value = so.Id
        JOIN OwnerTypes ot ON ot.Id = sk.OwnerTypeId 
            AND ot.Value = 'WORKSPACE'
    WHERE sk.KeyName = 'Visibility'
)
SELECT 
    w.Name,
    svfw.Value
FROM Workspaces w
    JOIN SettingValueForWorkspace svfw ON svfw.OwnerId = w.Id
WHERE w.Id = 1;

-- 11. Get 4 suggested Boards by Template Category Type with status as Template
SELECT TOP 4 
    b.Name,
    b.BackgroundUrl
FROM Boards b
    JOIN Templates t ON t.BoardId = b.Id
    JOIN TemplateCategories tc ON t.TemplateCategoryId = tc.Id
WHERE tc.Name = 'Operator'
ORDER BY 
    t.Viewed DESC, 
    t.Copied DESC;

-- 12. Get "Your boards" section: Get Boards belonging to Workspace where User is also a Member of the Board
SELECT 
    b.Name,
    b.BackgroundUrl
FROM Boards b
    JOIN Members m ON m.OwnerId = b.Id
    JOIN Workspaces w ON w.Id = b.WorkspaceId
    JOIN OwnerTypes ot ON ot.Id = m.OwnerTypeId 
        AND ot.Value = 'BOARD'
WHERE w.Id = 1 
    AND m.UserId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 6: MEMBER TAB OF WORKSPACE (SLIDE 10)
-- -----------------------------------------------------------------------------

-- 13. Get all Members in Workspace, number of Boards in Workspace that Member participates in and corresponding permissions in Workspace
WITH WorkspaceMembers AS (
    SELECT 
        m.UserId, 
        m.PermissionId
    FROM Members m
        JOIN OwnerTypes ot ON ot.Id = m.OwnerTypeId
    WHERE ot.Value = 'WORKSPACE' 
        AND m.OwnerId = 1
),
BoardInWorkspace AS (
    SELECT b.Id AS BoardId
    FROM Boards b
    WHERE b.WorkspaceId = 1
),
BoardMembers AS (
    SELECT 
        m.UserId,
        m.OwnerId AS BoardId
    FROM Members m
        JOIN OwnerTypes ot ON ot.Id = m.OwnerTypeId
    WHERE ot.Value = 'BOARD'
)
SELECT
    u.Username, 
    u.LastActive,
    p.Name,
    COUNT(bm.BoardId) AS NumBoardsJoined
FROM WorkspaceMembers wm
    LEFT JOIN BoardMembers bm ON bm.UserId = wm.UserId
    LEFT JOIN BoardInWorkspace biw ON bm.BoardId = biw.BoardId
    JOIN Users u ON wm.UserId = u.Id
    JOIN Permissions p ON wm.PermissionId = p.Id
GROUP BY 
    wm.UserId, 
    u.Username, 
    u.LastActive, 
    p.Name;

-- 14. Get sharelink of workspace and status of sharelink
SELECT 
    sl.Token,
    sl.Status,
    p.Name
FROM ShareLinks sl
    JOIN Workspaces w ON w.Id = sl.OwnerId
    JOIN Permissions p ON sl.PermissionId = p.Id
WHERE w.Id = 1;

-- 15. Update ShareLink Status of Workspace
UPDATE ShareLinks 
SET Status = 1
WHERE OwnerId = 1
    AND OwnerTypeId IN (
        SELECT Id
        FROM OwnerTypes
        WHERE Value = 'WORKSPACE'
    );

-- -----------------------------------------------------------------------------
-- SCREEN 7: GUEST TAB IN WORKSPACE MEMBER
-- -----------------------------------------------------------------------------

-- 16. Query Members of Boards belonging to Workspace, but Members who are not part of the Workspace
INSERT INTO Members (UserId, PermissionId, OwnerTypeId, OwnerId, InvitedBy, JoinedAt, Status) 
VALUES (1000, 1, 1, 3, 1, '', 'ACTIVE');

WITH WorkspaceBoardMembers AS (
    SELECT 
        m.UserId,
        b.Id AS BoardId,
        b.WorkspaceId
    FROM Members m 
        JOIN Boards b ON b.Id = m.OwnerId
        JOIN OwnerTypes ot ON ot.Id = m.OwnerTypeId 
            AND ot.Value = 'BOARD'
    WHERE b.WorkspaceId = 3
),
WorkspaceMembers AS (
    SELECT m.UserId
    FROM Members m
        JOIN Workspaces w ON w.Id = m.OwnerId
        JOIN OwnerTypes ot ON ot.Id = m.OwnerTypeId 
            AND ot.Value = 'WORKSPACE'
    WHERE m.OwnerId = 3
)
SELECT 
    u.Username,
    u.LastActive, 
    COUNT(wbm.BoardId) AS BoardMemberCount
FROM WorkspaceBoardMembers wbm
    LEFT JOIN WorkspaceMembers wm ON wbm.UserId = wm.UserId
    JOIN Users u ON u.Id = wbm.UserId
WHERE wm.UserId IS NULL
GROUP BY 
    u.Username,
    u.LastActive;

-- -----------------------------------------------------------------------------
-- SCREEN 8: SHARE BOARD
-- -----------------------------------------------------------------------------

-- 17. Add Member to Board with Permission
INSERT INTO Members (UserId, PermissionId, OwnerTypeId, OwnerId, InvitedBy, JoinedAt, Status) 
VALUES (1001, 1, 3, 3, 1, '', 'ACTIVE');

-- 18. Create ShareLink for Board with Permission (Each Board has only 1 ShareLink)
IF NOT EXISTS (
    SELECT 1 
    FROM ShareLinks sl 
        JOIN OwnerTypes ot ON ot.Id = sl.OwnerTypeId 
            AND ot.Value = 'BOARD'
    WHERE sl.OwnerId = 1
)
BEGIN 
    INSERT INTO ShareLinks (OwnerTypeId, OwnerId, PermissionId, Token, Status) 
    VALUES (3, 1, 1, '/path', 1);
END
ELSE
BEGIN
    PRINT 'Record already exists, skipping insert.';
END

-- 19. Update Status and Permission of ShareLink
UPDATE ShareLinks
SET Status = 'ENABLED',        
    PermissionId = 2           
WHERE OwnerId = 1
    AND OwnerTypeId IN (
        SELECT Id 
        FROM OwnerTypes 
        WHERE Value = 'BOARD'
    );

-- 20. Get all Members of Board and their Permissions
SELECT 
    m.Id, 
    u.Username,
    p.Name
FROM Members m
    JOIN Boards b ON b.Id = m.OwnerId
    JOIN Permissions p ON p.Id = m.PermissionId
    JOIN Users u ON u.Id = m.UserId
WHERE b.Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 9: WORKSPACE SETTING TAB
-- -----------------------------------------------------------------------------

-- 21. SettingKeys and corresponding SettingOptions for Workspace
SELECT 
    sk.KeyName, 
    so.DisplayValue
FROM SettingKeys sk
    JOIN SettingKeySettingOptions skso ON skso.SettingKeyId = sk.Id
    JOIN SettingOptions so ON so.Id = skso.SettingOptionId
    JOIN OwnerTypes ot ON ot.Id = sk.OwnerTypeId 
        AND ot.Value = 'WORKSPACE';

-- 22. SettingValues of specific Workspace
SELECT 
    sk.KeyName,
    sk.TypeValue,
    sv.Value,
    so.DisplayValue
FROM SettingValues sv
    JOIN SettingKeys sk ON sk.Id = sv.SettingKeyId
    JOIN SettingOptions so ON so.Id = sv.Value
    JOIN OwnerTypes ot ON ot.Id = sk.OwnerTypeId 
        AND ot.Value = 'WORKSPACE'
WHERE sv.OwnerId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 10: BOARD SETTINGS
-- -----------------------------------------------------------------------------

-- 23. SettingKeys with KeyName='permission.*' and corresponding SettingOptions for Board
SELECT 
    sk.KeyName, 
    so.DisplayValue
FROM SettingKeys sk
    JOIN SettingKeySettingOptions skso ON skso.SettingKeyId = sk.Id
    JOIN SettingOptions so ON so.Id = skso.SettingOptionId
    JOIN OwnerTypes ot ON ot.Id = sk.OwnerTypeId 
        AND ot.Value = 'BOARD'
WHERE sk.KeyName LIKE 'permissions.%';

-- 24. SettingKeys and corresponding SettingOptions for Board
SELECT 
    sk.KeyName, 
    so.DisplayValue
FROM SettingKeys sk
    JOIN SettingKeySettingOptions skso ON skso.SettingKeyId = sk.Id
    JOIN SettingOptions so ON so.Id = skso.SettingOptionId
    JOIN OwnerTypes ot ON ot.Id = sk.OwnerTypeId 
        AND ot.Value = 'BOARD';

-- 25. SettingValues corresponding to Board
SELECT 
    sv.OwnerId,
    sk.KeyName,
    sk.TypeValue,
    sv.Value,
    so.DisplayValue
FROM SettingValues sv
    JOIN SettingKeys sk ON sk.Id = sv.SettingKeyId
    JOIN SettingOptions so ON so.Id = sv.Value
    JOIN OwnerTypes ot ON ot.Id = sk.OwnerTypeId 
        AND ot.Value = 'BOARD'
WHERE sv.OwnerId = 3;

-- -----------------------------------------------------------------------------
-- SCREEN 11: WORKSPACE POWER-UP
-- -----------------------------------------------------------------------------

-- 26. Query how many Boards in Workspace have Power-Ups added
-- 1. Filter Boards belonging to Workspace (BoardId, WorkspaceId)
-- 2. Filter Power-Ups added to Boards (PowerUpId, BoardId)
-- 3. List Power-ups and number of Boards in Workspace where Power-ups are added
WITH BoardInWorkspace AS (
    SELECT 
        b.Id AS BoardId,
        b.WorkspaceId
    FROM Boards b
    WHERE b.WorkspaceId = 1
),
PowerUpInBoards AS (
    SELECT 
        pu.Id AS PowerUpId,
        pu.Name,
        pu.IconUrl,
        b.Id AS BoardId
    FROM PowerUps pu
        JOIN BoardPowerUps bpu ON bpu.PowerUpId = pu.Id
        JOIN Boards b ON b.Id = bpu.BoardId
)
SELECT 
    puib.Name,
    puib.IconUrl,
    COUNT(puib.BoardId) AS BoardUse
FROM PowerUpInBoards puib 
    JOIN BoardInWorkspace biw ON biw.BoardId = puib.BoardId
GROUP BY 
    puib.Name,
    puib.IconUrl;

-- -----------------------------------------------------------------------------
-- SCREEN 12: POWER-UPS DETAIL
-- -----------------------------------------------------------------------------

-- 27. Query Power-Up details
SELECT 
    pu.Name,
    pu.IconUrl,
    pu.AuthorName,
    pu.Description,
    pu.EmailContact,
    pu.PolicyUrl,
    pu.IsStaffPick,
    pu.IsIntegration,
    put.Name AS CategoryName
FROM PowerUps pu
    JOIN PowerUpCategories put ON put.Id = pu.PowerUpCategoryId
WHERE pu.Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 13: BILLING PLAN
-- -----------------------------------------------------------------------------

-- 28. Get Billing Plan
SELECT 
    bp.Name, 
    bp.PricePerUser, 
    bp.Type
FROM BillingPlans bp;

-- -----------------------------------------------------------------------------
-- SCREEN 14: BILLING WHEN HAVE SUBSCRIPTION
-- -----------------------------------------------------------------------------

-- 29. Get Subscription of specific Workspace
SELECT 
    bp.Name, 
    s.EndDate,
    bp.PricePerUser,
    s.MemberCountBilled,
    bc.Name,
    bc.Email,
    so.DisplayValue AS Language
FROM Subscriptions s
    JOIN BillingPlans bp ON bp.Id = s.BillingPlanId
    JOIN BillingContacts bc ON s.BillingContactId = bc.Id
    JOIN SettingOptions so ON so.Id = bc.Language
WHERE bc.WorkspaceId = 1;

-- 30. Change Payment Information
UPDATE PaymentInformations 
SET 
    CardNumber = '4628151718263',
    CardBrand = 'VISA',
    ExpirationDate = '24/12/2025',
    CVV = '247',
    Country = 'Vietnam'
WHERE BillingContactId = 1;

-- 31. Change BillingContact Information
UPDATE BillingContacts
SET Name = 'Quan Phan', 
    Email = 'huyhoangnguyen1002@gmail.com'
WHERE WorkspaceId = 1;

-- 32. Change Additional invoice details
UPDATE BillingContacts
SET AdditionalInvoiceDetail = 'TBD'
WHERE WorkspaceId = 1;

-- 33. Query Billing history from Subscription
SELECT 
    s.StartDate, 
    s.EndDate,
    bp.Name,
    bp.Type,
    bp.PricePerUser,
    s.MemberCountBilled,
    bp.PricePerUser * s.MemberCountBilled AS TotalBill
FROM Subscriptions s
    JOIN BillingPlans bp ON bp.Id = s.BillingPlanId
    JOIN BillingContacts bc ON bc.Id = s.BillingContactId
WHERE bc.WorkspaceId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 15: EXPORT WORKSPACE
-- -----------------------------------------------------------------------------

-- 34. Create Export for Workspace
INSERT INTO Exports (WorkspaceId, CreatedBy, CreatedAt, Size) 
VALUES (1, 1, '', 255);

-- 35. Query Export from specific Workspace
SELECT 
    e.CreatedAt,
    e.Size
FROM Exports e
WHERE e.WorkspaceId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 16: USER PROFILE AND VISIBILITY
-- -----------------------------------------------------------------------------

-- 36. Query Username and Bio
SELECT 
    u.Username,
    u.Bio
FROM Users u
WHERE u.Id = 1;

-- 37. Update Username and Bio
UPDATE Users
SET Username = 'flame',
    Bio = 'YPP4'
WHERE Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 17: BOARD SCREEN
-- -----------------------------------------------------------------------------

-- 38. Create Board
INSERT INTO Boards (Name, Description, CreatedAt, CreatedBy, AccessedAt, IsStar, BackgroundUrl, WorkspaceId, Status) 
VALUES ('bbv-VietNam', '', '', 1, '', 0, 'image.png', 1, 'ACTIVE');

-- 39. Create Stage in Board
INSERT INTO Stages (Title, CreatedAt, BoardId, Status, Position)
VALUES ('bbv', '', 1, 'ACTIVE', 1);

-- -----------------------------------------------------------------------------
-- SCREEN 18: STAGE POSITION
-- -----------------------------------------------------------------------------

-- 40. Update Stage Position
UPDATE Stages
SET BoardId = 1,
    Position = 5
WHERE BoardId = 5;

-- -----------------------------------------------------------------------------
-- SCREEN 19: STAGE CARD
-- -----------------------------------------------------------------------------

-- 41. Query all card in Stage
WITH CountAttachmentCardStage AS (
    SELECT 
        c.Id AS CardId,
        COUNT(a.Id) AS CountAttachment
    FROM Cards c
        LEFT JOIN Attachments a ON a.CardId = c.Id
    WHERE c.StageId = 1
    GROUP BY c.Id
),
LabelAgg AS (
    SELECT
        cl.CardId,
        STRING_AGG(l.Title, ', ') WITHIN GROUP (ORDER BY l.Title) AS Labels
    FROM CardLabels cl
        JOIN Labels l ON l.Id = cl.LabelId
    GROUP BY cl.CardId
),
MemberAgg AS (
    SELECT
        cam.CardId,
        STRING_AGG(u.Username, ', ') WITHIN GROUP (ORDER BY u.Username) AS AssignedMembers
    FROM CardAssignMembers cam
        JOIN Members m ON m.Id = cam.MemberId
        JOIN Users u ON u.Id = m.UserId
    GROUP BY cam.CardId
)
SELECT
    c.Id,
    c.Title,
    c.CoverType,
    c.CoverValue,
    c.StartDate,
    c.DueDate,
    c.Location,
    c.Position,
    c.Status,
    la.Labels,
    ma.AssignedMembers,
    cacs.CountAttachment
FROM Cards c
    JOIN Stages s ON c.StageId = s.Id
    LEFT JOIN CountAttachmentCardStage cacs ON c.Id = cacs.CardId
    LEFT JOIN LabelAgg la ON c.Id = la.CardId
    LEFT JOIN MemberAgg ma ON c.Id = ma.CardId
WHERE c.StageId = 1
ORDER BY c.Position;

-- -----------------------------------------------------------------------------
-- SCREEN 20: CARD DETAIL
-- -----------------------------------------------------------------------------

-- 42. Query Card Detail
WITH MemberAgg AS (
    SELECT
        cam.CardId,
        STRING_AGG(u.Username, ', ') WITHIN GROUP (ORDER BY u.Username) AS AssignedMembers
    FROM CardAssignMembers cam
        JOIN Members m ON m.Id = cam.MemberId
        JOIN Users u ON u.Id = m.UserId
    GROUP BY cam.CardId
),
LabelAgg AS (
    SELECT
        cl.CardId,
        STRING_AGG(l.Title, ', ') WITHIN GROUP (ORDER BY l.Title) AS Labels
    FROM CardLabels cl
        JOIN Labels l ON l.Id = cl.LabelId
    GROUP BY cl.CardId
)
SELECT 
    c.Title,
    c.Description,
    c.StartDate,
    c.DueDate,
    c.Location
FROM Cards c
    LEFT JOIN MemberAgg ma ON c.Id = ma.CardId
    LEFT JOIN LabelAgg la ON c.Id = la.CardId
WHERE c.Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 21: CARD COMMENT, CARD POSITION
-- -----------------------------------------------------------------------------

-- 43. Query Comment
SELECT 
    comment.Content,
    comment.CreatedAt
FROM Comments comment
    JOIN Cards c ON c.Id = comment.CardId
WHERE c.Id = 1;

-- 44. Query Activities related to Card
SELECT 
    u.Username,
    a.Description,
    a.CreatedAt
FROM Activities a
    JOIN Users u ON u.Id = a.UserId
    JOIN OwnerTypes ot ON ot.Id = a.OwnerTypeId 
        AND ot.Value = 'CARD'
WHERE a.OwnerId = 1;
Go
-- 45. Get MovedCard Data (Store Procedure)
create procedure sp_GetMoveCardData
    @UserId int,
    @SelectedBoardId int = null,
    @SelectedListId int = null
as
begin
    set NOCOUNT ON;
    -- Get Boards User is Member
    select
        b.Id as BoardId,
        b.Name as BoardName
    from Boards b
    join Members m on m.OwnerId=b.Id
    where m.UserId= @UserId
    -- Get Stage in Board
    if @SelectedBoardId is not null
    begin
        select
            s.Id as StageId,
            s.Title as StageTitle
        from Stages s
        where s.BoardId=@SelectedBoardId
        order by s.Position asc
    end
    -- Get MaxPosition Card can be in Stage
    if @SelectedListId is not null
    begin
        select
            count(*) +1 as MaxPosition
        from Cards c
        where c.StageId=@SelectedListId
    end
end
Go
EXEC sp_GetMoveCardData @UserId = 1, @SelectedBoardId = 1, @SelectedListId = 1;
--46. Query current position of Card
select c.Position as CardPosition,s.Title as StageTitle,b.Name as BoardName
from Cards c
join Stages s on s.Id=c.StageId
join Boards b on s.BoardId=b.id
where c.Id=1
--47.Move card to a different list
DECLARE @CardId INT = 42;
DECLARE @TargetListId INT = 5;
DECLARE @NewPosition INT = 2;

BEGIN TRANSACTION;

UPDATE Cards
SET Position = Position + 1
WHERE StageId = @TargetListId
  AND Position >= @NewPosition;

UPDATE Cards
SET 
    StageId = @TargetListId,
    Position = @NewPosition
WHERE Id = @CardId;

COMMIT;

-- -----------------------------------------------------------------------------
-- SCREEN 22: CARD COVER
-- -----------------------------------------------------------------------------

--48. Update Card Cover (Color, Unsplash, Attachment)
-- Update Card Cover to Color 
update Cards
set CoverType='COLOR', CoverValue='5'
where Id=1
-- Update Card Cover to Unsplash Image
update Cards
set CoverType='UNSPLASH', CoverValue='unsplashimage.png'
where Id=1
-- Update Card Cover to Attachment
update Cards
set CoverType='ATTACHMENT', CoverValue=''
where Id=1

-- -----------------------------------------------------------------------------
-- SCREEN 23: LABEL AND COLOR (Slide 32)
-- -----------------------------------------------------------------------------

-- 49. Get all Label and check Label added to Card
select
    l.Title as LabelTitle,
    c.Name as Color,
    c.Icon as ColorIcon,
    case    
        when cl.CardId is not null then 1
        else 0
    end as IsChecked
from Labels l
join Colors c on c.Id=l.ColorId
left join CardLabels cl on cl.LabelId=l.Id
where cl.CardId=1
order by l.Title
--50. Get all Color 
select top 10
    c.Name,c.Icon
from Colors c


