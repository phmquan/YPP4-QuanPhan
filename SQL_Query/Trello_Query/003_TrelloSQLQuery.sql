-- -----------------------------------------------------------------------------
-- SCREEN 1: TAB BOARDS (SLIDE 4)
-- -----------------------------------------------------------------------------
-- Query Starred Board By User
select b.Id,b.BoardName,b.BackgroundUrl
from UserStarredBoards usb
join Boards b on b.Id=usb.BoardId
where usb.UserId=1
order by CreatedAt desc
-- 1. Query 4 suggested templates by Template Category
SELECT TOP 4 
    t.Id as TemplateId,
    t.Title, 
    t.BackgroundUrl
FROM Templates t
    JOIN Categories c ON t.CategoryId = c.Id
WHERE c.CategoryName = 'User'; -- ':templateCategory'

-- 2. Query 4 recently accessed Boards by user
SELECT TOP 4 
    b.Id as BoardId,
    b.BoardName,
    b.BackgroundUrl
FROM Boards b
    JOIN UserViewHistories uvh ON uvh.OwnerId  = b.Id
    JOIN Categories c on c.Id=uvh.CategoryId and c.CategoryName='Board'
    JOIN Users u ON u.Id = uvh.UserId
WHERE u.Id = 1 -- userId
ORDER BY uvh.AccessedAt DESC;

-- 3. Query all Workspaces where User is a Member
SELECT 
    w.Id, 
    w.WorkspaceName
FROM Workspaces w
    JOIN Members m ON m.OwnerId = w.Id
    JOIN Categories ot ON m.CategoryId = ot.Id
WHERE ot.CategoryName = 'WORKSPACE' 
    AND m.UserId = 1;
    
-- 4. Query all Workspaces where User is a Member. 
--    For each workspace, get all Boards where User is also a Member
SELECT 
    w.Id AS WorkspaceId,
    w.WorkspaceName,
    w.LogoUrl AS WorkspaceIcon,
    b.Id AS BoardId,
    b.BoardName AS BoardName,
    b.BoardDescription,
    b.BackgroundUrl,
    b.CreatedAt
FROM Workspaces w
    -- Find Workspaces where User is a Member
    JOIN Members mw ON mw.OwnerId = w.Id
    JOIN Categories otw ON otw.Id = mw.CategoryId 
        AND otw.CategoryName = 'WORKSPACE'
    -- Find Boards corresponding to Workspace where User is also a Member
    JOIN Boards b ON b.WorkspaceId = w.Id
    JOIN Members mb ON mb.OwnerId = b.Id
    JOIN Categories otb ON otb.Id = mb.CategoryId 
        AND otb.CategoryName = 'BOARD'
WHERE mb.UserId = 1
    

-- 5. Query all closed boards where user is a member
SELECT 
    b.Id as BoardID,
    b.BoardName, 
    w.Id as WorkspaceId,
    w.WorkspaceName
FROM Boards b
    JOIN Workspaces w ON w.Id = b.WorkspaceId
    JOIN Members m ON m.OwnerId = b.Id
    JOIN Categories ot ON m.CategoryId = ot.Id 
        AND ot.CategoryName = 'BOARD'
WHERE m.UserId = 3
    AND b.BoardStatus = 'CLOSED';
--6. Delete Or Reopen a closed board
DECLARE @BoardId INT
UPDATE Boards
SET BoardStatus='CLOSED' -- or  'ACTIVE'
WHERE Id=3

-- -----------------------------------------------------------------------------
-- SCREEN 2: TEMPLATES TAB (SLIDE 5)
-- -----------------------------------------------------------------------------

-- 7. Get top 14 template categories
Select Top 7
    c.Id,c.CategoryName,c.Icon
from Categories c
join CategoryTypes ct on ct.Id=c.CategoryTypeId and ct.Id=6


-- 8. Get New and notable templates
SELECT 
    t.Id as TemplateId,
    t.Title,
    t.BackgroundUrl,
    t.CreatedAt,
    t.CreatedBy,
    t.Copied,
    t.Viewed,
    t.TemplateDescription
FROM Templates t
ORDER BY 
    t.CreatedAt DESC, 
    t.Viewed DESC, 
    t.Copied DESC;
go
-- -----------------------------------------------------------------------------
-- SCREEN 3: TEMPLATE DETAIL (SLIDE 6)
-- -----------------------------------------------------------------------------

-- 9. Get template details and the Board associated with that template
--9.1.Get All Template Detail
SELECT 
    t.Id as TemplateId,
    t.Title, 
    u.Username,
    t.Copied,
    t.Viewed,
    t.TemplateDescription,
    b.Id as BoardId,
    b.BoardName,
    b.BoardStatus
FROM Templates t
    JOIN Boards b ON b.Id = t.BoardId
    JOIN Users u ON t.CreatedBy = u.Id
WHERE t.Id = 1; -- templateId
GO
--9.2.Get all Stage, Card belong to Board in Template (Store Procedure)

ALTER PROCEDURE GetBoardDetail
    @BoardId INT
AS
BEGIN
    --Get BoardDetail
    SELECT 
        b.Id as BoardId,
        b.BoardName,
        b.BackgroundUrl,
        b.BoardStatus
    FROM Boards b
    WHERE b.Id=@BoardId
    --Get Stage in Board
    SELECT s.Id as StageId, s.Title as StageTitle,s.Position as StagePosition,c.ColorName as BackgroundColor
    FROM Stages s
    JOIN Colors c on c.Id=s.ColorId
    where s.BoardId=@BoardId
    --Get Card in Stage
    SELECT 
        c.Id as CardId,
        c.Title as CardTitle,
        c.StageId,
        c.CoverValue,
        c.CoverValue,
        color.ColorName,
        a.AttachmentPath,
        c.Position,
        c.CardDescription
    FROM CARDS c
    JOIN Stages s on s.Id=c.StageId
    LEFT JOIN Colors color on TRY_CAST(CASE WHEN c.CategoryId = 60 THEN c.CoverValue ELSE NULL END AS INT) = color.Id
    LEFT JOIN Attachments a on TRY_CAST(CASE WHEN c.CategoryId = 61 THEN c.CoverValue ELSE NULL END AS INT) = a.Id
    where s.BoardId=@BoardId
    order by StageId
END
GO
EXEC GetBoardDetail @BoardId=1

-- -----------------------------------------------------------------------------
-- SCREEN 4: CREATE WORKSPACE (SLIDE 7)
-- -----------------------------------------------------------------------------

-- 10. Insert data into Workspaces
INSERT INTO Workspaces (WorkspaceName, WorkspaceDescription, CategoryId) 
VALUES ('Quan', 'BBV-YPP4', 'ENGINEERING_IT');

-- -----------------------------------------------------------------------------
-- SCREEN 5: TAB BOARDS IN WORKSPACE WITH USERID = 1 (SLIDE 8)
-- -----------------------------------------------------------------------------

-- 11. Get Workspace Name, SettingKey='visibility' and SettingValue 
--     related to SettingKeys of Workspace
WITH SettingValueForWorkspace AS (
    SELECT 
        sv.Id as SettingValueId,
        sv.OwnerId,
        sk.Id,
        sk.CategoryId,
        sk.KeyName,
        so.DisplayValue 
    FROM SettingValues sv
        JOIN SettingKeys sk ON sk.Id = sv.SettingKeyId
        LEFT JOIN SettingOptions so ON sv.SettingValue= so.Id 
            AND sk.CategoryId=7
        JOIN Categories ot ON ot.Id = sk.CategoryId 
            AND ot.CategoryName = 'WORKSPACE'
    WHERE sk.KeyName = 'Visibility'
)
SELECT 
    w.Id as WorkspaceId,
    w.WorkspaceName,
    svfw.DisplayValue as Visibility
FROM Workspaces w
    JOIN SettingValueForWorkspace svfw ON svfw.OwnerId = w.Id
WHERE w.Id = 2;

-- 12. Get 4 suggested Boards by Template Category Type 
SELECT TOP 4 
    b.Id,
    b.BoardName,
    b.BackgroundUrl
FROM Boards b
    JOIN Templates t ON t.BoardId = b.Id
    JOIN Categories tc ON t.CategoryId = tc.Id
WHERE tc.CategoryName = 'Operator'
ORDER BY 
    t.Viewed DESC, 
    t.Copied DESC;

-- 13. Get "Your boards" section: Get Boards belonging to Workspace 
--     where User is also a Member of the Board
SELECT 
    b.Id as BoardId,
    b.BoardName,
    b.BackgroundUrl
FROM Boards b
    JOIN Members m ON m.OwnerId = b.Id
    JOIN Workspaces w ON w.Id = b.WorkspaceId
    JOIN Categories ot ON ot.Id = m.CategoryId 
        AND ot.CategoryName = 'BOARD'
WHERE w.Id = 1 
    

-- -----------------------------------------------------------------------------
-- SCREEN 6: MEMBER TAB OF WORKSPACE (SLIDE 10)
-- -----------------------------------------------------------------------------

-- 14. Get all Members in Workspace, number of Boards in Workspace that Member 
--     participates in and corresponding RolePermissions in Workspace
WITH WorkspaceMembers AS (
    SELECT 
        m.UserId, 
        m.RolePermissonId
    FROM Members m
        JOIN Categories ot ON ot.Id = m.CategoryId
    WHERE ot.CategoryName = 'WORKSPACE' 
        AND m.OwnerId = 1
),
BoardInWorkspace AS (
    SELECT 
        b.Id AS BoardId,
        b.BoardName as BoardName,
        b.BackgroundUrl
    FROM Boards b
    WHERE b.WorkspaceId = 1
),
BoardMembers AS (
    SELECT 
        m.UserId,
        m.OwnerId AS BoardId
    FROM Members m
        JOIN Categories ot ON ot.Id = m.CategoryId
    WHERE ot.CategoryName = 'BOARD'
)
SELECT
    u.Id as UserId,
    u.Username, 
    u.Email as UserEmail,
    u.LastActive,
    p.PermissionName as Permission,
    COUNT(bm.BoardId) AS NumBoardsJoined,
    STRING_AGG(biw.BoardName, ', ') AS JoinedBoardNames,
    STRING_AGG(biw.BackgroundUrl, ', ') AS JoinedBoardBackground
FROM WorkspaceMembers wm
    LEFT JOIN BoardMembers bm ON bm.UserId = wm.UserId
    JOIN BoardInWorkspace biw ON bm.BoardId = biw.BoardId
    JOIN Users u ON wm.UserId = u.Id
    JOIN RolePermissions p ON wm.RolePermissonId = p.Id
GROUP BY 
    u.Id, 
    u.Username,
    u.Email,
    u.LastActive, 
    p.PermissionName;

-- 15. Get sharelink of workspace and status of sharelink
SELECT
    sl.Id as ShareLinkId,
    sl.ShareLinkToken,
    sl.ShareLinkStatus,
    p.PermissionName
FROM ShareLinks sl
    JOIN Workspaces w ON w.Id = sl.OwnerId
    JOIN Categories c on c.Id=sl.CategoryId AND c.CategoryName='WORKSPACE'
    JOIN RolePermissions p ON sl.RolePermissonId = p.Id
WHERE w.Id = 1;

-- 16. Update ShareLink Status of Workspace
UPDATE ShareLinks 
SET ShareLinkStatus = 1
WHERE OwnerId = 1
    AND CategoryId IN (
        SELECT Id
        FROM Categories
        WHERE CategoryName = 'WORKSPACE'
    );

-- -----------------------------------------------------------------------------
-- SCREEN 7: GUEST TAB IN WORKSPACE MEMBER
-- -----------------------------------------------------------------------------

--17. Query Members of Boards belonging to Workspace, but Members who are not part of the Workspace
WITH WorkspaceBoardMembers AS (
    SELECT 
        m.UserId,
        b.Id AS BoardId,
        b.WorkspaceId
    FROM Members m 
        JOIN Boards b ON b.Id = m.OwnerId
        JOIN Categories ot ON ot.Id = m.CategoryId 
            AND ot.CategoryName = 'BOARD'
    WHERE b.WorkspaceId = 3
),
WorkspaceMembers AS (
    SELECT m.UserId
    FROM Members m
        JOIN Workspaces w ON w.Id = m.OwnerId
        JOIN Categories ot ON ot.Id = m.CategoryId 
            AND ot.CategoryName = 'WORKSPACE'
    WHERE m.OwnerId = 3
)
SELECT
    u.Id,
    u.Username,
    u.LastActive, 
    COUNT(wbm.BoardId) AS BoardMemberCount
FROM WorkspaceBoardMembers wbm
    LEFT JOIN WorkspaceMembers wm ON wbm.UserId = wm.UserId
    JOIN Users u ON u.Id = wbm.UserId
WHERE wm.UserId IS NULL
GROUP BY 
    u.Id,
    u.Username,
    u.LastActive;

-- -----------------------------------------------------------------------------
-- SCREEN 8: SHARE BOARD (Slide 12)
-- -----------------------------------------------------------------------------

-- 18. Add Member to Board with Permission
INSERT INTO Members (UserId, RolePermissonId, CategoryId, OwnerId, InvitedBy, JoinedAt, MemberStatus) 
VALUES (1001, 1, 3, 3, 1, '', 'ACTIVE');

-- 19. Create ShareLink for Board with Permission (Each Board has only 1 ShareLink)
IF NOT EXISTS (
    SELECT 1 
    FROM ShareLinks sl 
        JOIN Categories ot ON ot.Id = sl.CategoryId 
            AND ot.CategoryName = 'BOARD'
    WHERE sl.OwnerId = 1
)
BEGIN 
    INSERT INTO ShareLinks (CategoryId, OwnerId, RolePermissonId, ShareLinkToken, ShareLinkStatus) 
    VALUES (3, 1, 1, '/path', 1);
END
ELSE
BEGIN
    PRINT 'Record already exists, skipping insert.';
END;

--20. Update Status and Permission of ShareLink
UPDATE ShareLinks
SET ShareLinkStatus = 'ENABLED',        
    RolePermissonId = 2           
WHERE OwnerId = 1
    AND CategoryId IN (
        SELECT Id 
        FROM Categories 
        WHERE CategoryName = 'BOARD'
    );

--21. Get all Members of Board and their RolePermissions
SELECT 
    m.Id, 
    u.Username,
    p.PermissionName
FROM Members m
    JOIN Boards b ON b.Id = m.OwnerId
    JOIN RolePermissions p ON p.Id = m.RolePermissonId
    JOIN Users u ON u.Id = m.UserId
WHERE b.Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 9: WORKSPACE SETTING TAB (Slide 14)
-- -----------------------------------------------------------------------------

-- 22. SettingKeys and corresponding SettingOptions for Workspace
SELECT 
    sk.KeyName, 
    so.DisplayValue
FROM SettingKeys sk
    JOIN SettingKeySettingOptions skso ON skso.SettingKeyId = sk.Id
    JOIN SettingOptions so ON so.Id = skso.SettingOptionId
    JOIN Categories ot ON ot.Id = sk.CategoryId 
        AND ot.CategoryName = 'WORKSPACE';

-- 23. SettingValues of specific Workspace

SELECT 
    sk.KeyName,
    sk.CategoryId,
    sv.SettingValue,
    so.DisplayValue
FROM SettingValues sv
    JOIN SettingKeys sk ON sk.Id = sv.SettingKeyId
    LEFT JOIN SettingOptions so ON so.Id = sv.SettingValue AND sk.IsBoolean=0
    
WHERE sv.OwnerId = 1 and sk.CategoryId=1;

-- -----------------------------------------------------------------------------
-- SCREEN 10: BOARD SETTINGS (Slide 15)
-- -----------------------------------------------------------------------------

-- 24. SettingKeys with KeyName='permission.*' and corresponding SettingOptions for Board
SELECT
    sk.Id as SettingKeyId,
    sk.KeyName,
    sk.CategoryId,
    so.DisplayValue
FROM SettingKeys sk
    JOIN SettingKeySettingOptions skso ON skso.SettingKeyId = sk.Id
    LEFT JOIN SettingOptions so ON so.Id = skso.SettingOptionId AND sk.CategoryId=7
    JOIN Categories ot ON ot.Id = sk.CategoryId 
        AND ot.CategoryName = 'BOARD'
WHERE sk.KeyName LIKE 'permissions.%';

-- 25. SettingKeys and corresponding SettingOptions for Board
SELECT 
    sk.Id as SettingKeyId,
    sk.KeyName,
    sk.CategoryId,
    so.DisplayValue
FROM SettingKeys sk
    JOIN SettingKeySettingOptions skso ON skso.SettingKeyId = sk.Id
    LEFT JOIN SettingOptions so ON so.Id = skso.SettingOptionId AND sk.CategoryId=7
    JOIN Categories ot ON ot.Id = sk.CategoryId 
        AND ot.CategoryName = 'BOARD';

-- 26. SettingValues corresponding to Board
SELECT
    sv.Id as SettingValueId,
    sv.OwnerId,
    sk.KeyName,
    sk.CategoryId,
    sv.SettingValue,
    so.DisplayValue
FROM SettingValues sv
    JOIN SettingKeys sk ON sk.Id = sv.SettingKeyId
    LEFT JOIN SettingOptions so ON so.Id = sv.SettingValue AND sk.CategoryId=7
    JOIN Categories ot ON ot.Id = sk.CategoryId 
        AND ot.CategoryName = 'BOARD'
WHERE sv.OwnerId = 3;

-- -----------------------------------------------------------------------------
-- SCREEN 11: WORKSPACE POWER-UP (Slide 17)
-- -----------------------------------------------------------------------------

-- 27. Query how many Boards in Workspace have Power-Ups added
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
        pu.PowerUpName,
        pu.IconUrl,
        b.Id AS BoardId
    FROM PowerUps pu
        JOIN BoardPowerUps bpu ON bpu.PowerUpId = pu.Id
        JOIN Boards b ON b.Id = bpu.BoardId
)
SELECT 
    puib.PowerUpId as PowerUpId,
    puib.PowerUpName as PowerUpName,
    puib.IconUrl,
    COUNT(puib.BoardId) AS BoardUse
FROM PowerUpInBoards puib 
    JOIN BoardInWorkspace biw ON biw.BoardId = puib.BoardId
GROUP BY 
    puib.PowerUpId,
    puib.PowerUpName,
    puib.IconUrl;

-- -----------------------------------------------------------------------------
-- SCREEN 12: POWER-UPS DETAIL (Slide 18)
-- -----------------------------------------------------------------------------

-- 28. Query Power-Up details
SELECT 
    pu.Id as PowerUpId,
    pu.PowerUpName,
    pu.IconUrl,
    pu.AuthorName,
    pu.PowerUpDescription,
    pu.EmailContact,
    pu.PolicyUrl,
    pu.IsStaffPick,
    pu.IsIntegration,
    c.CategoryName AS CategoryName
FROM PowerUps pu
    JOIN Categories c ON c.Id = pu.CategoryId
WHERE pu.Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 13: BILLING PLAN (Slide 20)
-- -----------------------------------------------------------------------------

-- 29. Get Billing Plan
SELECT 
    bp.Id as BillingPlanId,
    bp.PlanName, 
    bp.PricePerUser, 
    bp.BillingPlanDescription
FROM BillingPlans bp;

-- -----------------------------------------------------------------------------
-- SCREEN 14: BILLING WHEN HAVE SUBSCRIPTION (Slide 21)
-- -----------------------------------------------------------------------------

--30. Get Subscription of specific Workspace
SELECT 
    s.Id as SubscriptionId,
    bp.PlanName, 
    s.EndDate,
    bp.PricePerUser,
    s.MemberCountBilled,
    bc.BillingContactName,
    bc.BillingContactEmail,
    so.DisplayValue AS Language
FROM Subscriptions s
    JOIN BillingPlans bp ON bp.Id = s.BillingPlanId
    JOIN BillingContacts bc ON s.BillingContactId = bc.Id
    JOIN SettingOptions so ON so.Id = bc.BillingLanguage
WHERE bc.WorkspaceId = 1;

--31. Change Payment Information
UPDATE PaymentInformations 
SET 
    CardNumber = '4628151718263',
    CardBrand = 'VISA',
    ExpirationDate = '24/12/2025',
    CVV = '247',
    Country = 'Vietnam'
WHERE BillingContactId = 1;

--32. Change BillingContact Information
UPDATE BillingContacts
SET 
    BillingContactName = 'Quan Phan', 
    BillingContactEmail = 'huyhoangnguyen1002@gmail.com'
WHERE WorkspaceId = 1;
-- -----------------------------------------------------------------------------
-- SCREEN 15: BILLING HISTORY (Slide 22)
-- -----------------------------------------------------------------------------
-- 33. Change Additional invoice details
UPDATE BillingContacts
SET AdditionalInvoiceDetail = 'TBD'
WHERE WorkspaceId = 1;

--34. Query Billing history from Subscription
SELECT 
    s.Id as SubscriptionId,
    s.StartDate, 
    s.EndDate,
    bp.PlanName,
    bp.BillingPlanDescription,
    bp.PricePerUser,
    s.MemberCountBilled,
    bp.PricePerUser * s.MemberCountBilled AS TotalBill
FROM Subscriptions s
    JOIN BillingPlans bp ON bp.Id = s.BillingPlanId
    JOIN BillingContacts bc ON bc.Id = s.BillingContactId
WHERE bc.WorkspaceId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 16: EXPORT WORKSPACE  (Slide 24)
-- -----------------------------------------------------------------------------

--35. Create Export for Workspace
INSERT INTO Exports (WorkspaceId, CreatedBy, CreatedAt, Size) 
VALUES (1, 1, '', 255);

--36. Query Export from specific Workspace
SELECT 
    e.Id as ExportId,
    e.CreatedAt,
    e.Size
FROM Exports e
WHERE e.WorkspaceId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 17: USER PROFILE AND VISIBILITY (Slide 25)
-- -----------------------------------------------------------------------------

--37. Query Username and Bio
SELECT 
    u.Id as UserId,
    u.Username,
    u.Bio
FROM Users u
WHERE u.Email = 'juancampos@lloyd.org';

--38. Update Username and Bio
UPDATE Users
SET 
    Username = 'flame',
    Bio = 'YPP4'
WHERE Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 18: BOARD SCREEN (Slide 26)
-- -----------------------------------------------------------------------------

--39. Create Board
INSERT INTO Boards (BoardName, BoardDescription, CreatedAt, CreatedBy, AccessedAt, IsStar, BackgroundUrl, WorkspaceId, BoardStatus) 
VALUES ('bbv-VietNam', '', '', 1, '', 0, 'image.png', 1, 'ACTIVE');

--40. Create Stage in Board
INSERT INTO Stages (Title, CreatedAt, BoardId, StageStatus, Position)
VALUES ('bbv', '', 1, 'ACTIVE', 1);

-- -----------------------------------------------------------------------------
-- SCREEN 19: STAGE POSITION (Slide 27)
-- -----------------------------------------------------------------------------

--41. Update Stage Position
UPDATE Stages
SET 
    BoardId = 1,
    Position = 5
WHERE BoardId = 5;

-- -----------------------------------------------------------------------------
-- SCREEN 20: STAGE CARD (Slide 28)
-- -----------------------------------------------------------------------------

--42. Query all card in Stage
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
        m.OwnerId AS CardId,
        STRING_AGG(u.Id, ', ') AS AssignedMemberId,
        STRING_AGG(u.Username, ', ') WITHIN GROUP (ORDER BY u.Username) AS AssignedMembers
    FROM Members m
        JOIN Categories ot ON ot.Id = m.CategoryId 
            AND ot.CategoryName = 'CARD'
        JOIN Users u ON u.Id = m.UserId
    GROUP BY m.OwnerId
)
SELECT
    c.Id,
    c.Title,
    c.CategoryId,
    c.CoverValue,
    c.StartDate,
    c.DueDate,
    c.CardLocation,
    c.Position,
    c.CardStatus,
    la.Labels,
    ma.AssignedMemberId,
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
-- SCREEN 21: CARD DETAIL (Slide 29)
-- -----------------------------------------------------------------------------

--43. Query Card Detail
WITH MemberAgg AS (
    SELECT
        m.OwnerId AS CardId,
        STRING_AGG(u.Id, ', ') AS AssignedMemberId,
        STRING_AGG(u.Username, ', ') WITHIN GROUP (ORDER BY u.Username) AS AssignedMembers
    FROM Members m
        JOIN Categories ot ON ot.Id = m.CategoryId 
            AND ot.CategoryName = 'CARD'
        JOIN Users u ON u.Id = m.UserId
    GROUP BY m.OwnerId
),
LabelAgg AS (
    SELECT
        cl.CardId,
        STRING_AGG(l.Id, ', ') WITHIN GROUP (ORDER BY l.Id) AS LabelId,
        STRING_AGG(l.Title, ', ')  AS LabelTitle
    FROM CardLabels cl
        JOIN Labels l ON l.Id = cl.LabelId
    GROUP BY cl.CardId
)
SELECT 
    c.Title,
    c.CardDescription,
    c.StartDate,
    c.DueDate,
    c.CardLocation,
    la.LabelId,
    la.LabelTitle,
    ma.AssignedMemberId,
    ma.AssignedMembers
FROM Cards c
    LEFT JOIN MemberAgg ma ON c.Id = ma.CardId
    LEFT JOIN LabelAgg la ON c.Id = la.CardId
WHERE c.Id = 27;

-- -----------------------------------------------------------------------------
-- SCREEN 22: CARD COMMENT, CARD POSITION (SLide 30)
-- -----------------------------------------------------------------------------

--44. Query Comment
SELECT 
    comment.Content,
    comment.CreatedAt
FROM Comments comment
    JOIN Cards c ON c.Id = comment.CardId
WHERE c.Id = 1;

--45. Query Activities related to Card
SELECT 
    u.Username,
    a.ActivityDescription,
    a.CreatedAt
FROM Activities a
    JOIN Users u ON u.Id = a.UserId
    JOIN Categories ot ON ot.Id = a.CategoryId 
        AND ot.CategoryName = 'CARD'
WHERE a.OwnerId = 1;
GO

--46. Get MovedCard Data (Store Procedure)
CREATE PROCEDURE sp_GetMoveCardData
    @UserId INT,
    @SelectedBoardId INT = NULL,
    @SelectedListId INT = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Get Boards User is Member
    SELECT
        b.Id AS BoardId,
        b.BoardName AS BoardName
    FROM Boards b
        JOIN Members m ON m.OwnerId = b.Id
    WHERE m.UserId = @UserId;
    
    -- Get Stage in Board
    IF @SelectedBoardId IS NOT NULL
    BEGIN
        SELECT
            s.Id AS StageId,
            s.Title AS StageTitle
        FROM Stages s
        WHERE s.BoardId = @SelectedBoardId
        ORDER BY s.Position ASC;
    END;
    
    -- Get MaxPosition Card can be in Stage
    IF @SelectedListId IS NOT NULL
    BEGIN
        SELECT
            COUNT(*) + 1 AS MaxPosition
        FROM Cards c
        WHERE c.StageId = @SelectedListId;
    END;
END;
GO

-- Execute stored procedure example
EXEC sp_GetMoveCardData @UserId = 1, @SelectedBoardId = 1, @SelectedListId = 1;

--47. Query current position of Card
SELECT 
    c.Id as CardId,
    c.Position AS CardPosition,
    s.Title AS StageTitle,
    b.BoardName AS BoardName
FROM Cards c
    JOIN Stages s ON s.Id = c.StageId
    JOIN Boards b ON s.BoardId = b.Id
WHERE c.Id = 1;

--48. Move card to a different list
DECLARE @CardId INT = 42;
DECLARE @TargetListId INT = 5;
DECLARE @NewPosition INT = 2;
-- Update positions of existing cards
UPDATE Cards
SET Position = Position + 1
WHERE StageId = @TargetListId
    AND Position >= @NewPosition;
-- Move the card to new position
UPDATE Cards
SET 
    StageId = @TargetListId,
    Position = @NewPosition
WHERE Id = @CardId;


-- -----------------------------------------------------------------------------
-- SCREEN 23: CARD COVER (Slide 31)
-- -----------------------------------------------------------------------------

-- 49. Update Card Cover (Color, Unsplash, Attachment)

-- Update Card Cover to Color 
UPDATE Cards
SET 
    CategoryId = 62, --Color
    CoverValue = '5'
WHERE Id = 1;

-- Update Card Cover to Unsplash Image
UPDATE Cards
SET 
    CategoryId= 63, --Unsplash
    CoverValue = 'unsplashimage.png'
WHERE Id = 1;

-- Update Card Cover to Attachment
UPDATE Cards
SET 
    CategoryId = 64, 
    CoverValue = ''
WHERE Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 24: LABEL AND COLOR (Slide 32)
-- -----------------------------------------------------------------------------

-- 50. Get all Label and check Label added to Card
SELECT
    l.Id AS LabelId,
    l.Title AS LabelTitle,
    c.ColorName AS Color,
    c.Icon AS ColorIcon,
    CASE    
        WHEN cl.CardId IS NOT NULL THEN 1
        ELSE 0
    END AS IsChecked
FROM Labels l
    LEFT JOIN Colors c ON l.ColorId = c.Id
    LEFT JOIN CardLabels cl ON l.Id = cl.LabelId AND cl.CardId = 10
ORDER BY IsChecked DESC;

-- 51. Get all Color 
SELECT TOP 10
    c.ColorName,
    c.Icon
FROM Colors c;
GO

-- -----------------------------------------------------------------------------
-- SCREEN 25: CHECKLIST AND CHECKLIST ITEM (Slide 34)
-- -----------------------------------------------------------------------------

-- 52. Query all CheckList in specific Card
SELECT 
    cl.Id AS CheckListId,
    cl.CheckListName AS CheckListName
FROM CheckLists cl
WHERE cl.CardId = 1;

-- 53. Query all CheckListItem in CheckList of specific Card
SELECT 
    cli.CheckListItemName AS ItemName,
    cli.DueDate AS DueDate,
    m.Id AS MemberId,
    u.Username AS Username,
    cli.CheckListItemStatus AS IsCompleteItem,
    cli.CheckListId AS CheckListID
FROM CheckListItems cli
    JOIN CheckLists cl ON cl.Id = cli.CheckListId
    LEFT JOIN Members m ON m.Id = cli.MemberId
    LEFT JOIN Users u ON u.Id = m.UserId
WHERE cl.CardId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 26: CARD ATTACHMENT (Slide 36)
-- -----------------------------------------------------------------------------

-- 54. Get all Attachment of a specific Card
SELECT 
    a.Id AS AttachmentId,
    a.AttachmentPath AS AttachmentLink,
    a.CategoryId AS AttachmentFileType,
    a.AttachmentName,
    a.CreatedAt
FROM Attachments a
    JOIN Cards c ON a.CardId = c.Id
WHERE c.Id = 1;
GO

-- -----------------------------------------------------------------------------
-- SCREEN 27: CUSTOM FIELD (Slide 38)
-- -----------------------------------------------------------------------------

-- 55. Query All CustomField, FieldItem of Board (store procedure)
CREATE PROCEDURE sp_GetAllCustomFieldAndFieldValue
    @BoardId INT
AS
BEGIN
    -- Query all CustomFields and FieldItem if have dropdown CustomField been add to Board
    SELECT 
        cf.Id,
        cf.Title,
        cf.Position,
        cf.CategoryId
    FROM CustomFields cf
    WHERE cf.BoardId = @BoardId;

    SELECT
        cf.Id as CustomFieldId,
        fi.Id as FieldItemId,
        fi.FieldItemValue AS FieldItemValue,
        fi.Position AS FieldItemPriority,
        c.ColorName AS Color,
        c.Icon AS ColorIcon
    FROM FieldItems fi
        JOIN CustomFields cf ON cf.Id = fi.CustomFieldId 
            AND cf.CategoryId = 1
        LEFT JOIN Colors c ON fi.ColorId=c.Id
    WHERE cf.BoardId = @BoardId;
END;
GO

-- Execute stored procedure example
EXEC sp_GetAllCustomFieldAndFieldValue @BoardId = 491;

-- 56. Query FieldValue of CustomField in specific Card
SELECT 
    cf.Title, 
    cf.CategoryId, 
    fv.FieldValue AS FieldValue, 
    fi.FieldItemValue AS FieldItemValue
FROM FieldValues fv
    JOIN CustomFields cf ON cf.Id = fv.CustomFieldId
    LEFT JOIN FieldItems fi ON fi.Id = TRY_CAST(fv.FieldValue AS INT) 
        AND cf.CategoryId = 1
WHERE fv.CardId = 10;

-- -----------------------------------------------------------------------------
-- SCREEN 28: CARD STICKER (Slide 40)
-- -----------------------------------------------------------------------------

-- 57. Query all Stickers
SELECT 
    st.Id, 
    st.StickerName,
    st.StickerUrl
FROM Stickers st;

-- 58. Add Sticker to Card
INSERT INTO CardStickers (CardId, StickerId, PositionX, PositionY) 
VALUES (1, 5, 10.5, 20.6);

-- 59. Query Sticker of all Card in specific Board
SELECT 
    sta.BoardId AS BoardId, 
    s.StickerName AS StickerName, 
    s.StickerUrl, 
    cs.CardId,
    cs.PositionX AS StickerPositionXAxis, 
    cs.PositionY AS StickerPositionYAxis,
    cs.IndexZ AS StickerPositionZIndex
FROM Stickers s
    JOIN CardStickers cs ON cs.StickerId = s.Id
    JOIN Cards c ON c.Id = cs.CardId
    JOIN Stages sta ON sta.Id = c.StageId
WHERE sta.BoardId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 29: HOME TAB (Slide 42)
-- -----------------------------------------------------------------------------

-- 60. Get user's recent activities across all accessible boards/workspaces
SELECT TOP 100
    a.Id AS ActivityId,
    a.ActivityDescription,
    a.CreatedAt,
    u.Username AS ActivityUser,
    u.PictureUrl AS UserPicture,
    -- Workspace and Board context
    w.WorkspaceName AS WorkspaceName,
    b.BoardName AS BoardName,
    -- Activity context
    CASE 
        WHEN ot.CategoryName = 'BOARD' THEN 'Board activity'
        WHEN ot.CategoryName = 'CARD' THEN 'Card: ' + c.Title
        WHEN ot.CategoryName = 'WORKSPACE' THEN 'Workspace activity'
        ELSE ot.CategoryName
    END AS ActivityDescription,
    ot.CategoryName AS ActivityType
FROM Activities a
    INNER JOIN Users u ON a.UserId = u.Id
    INNER JOIN Categories ot ON a.CategoryId = ot.Id
    -- Get all memberships for the current user
    INNER JOIN Members m ON m.UserId = 3
    INNER JOIN Categories mot ON m.CategoryId = mot.Id
    -- Context joins
    LEFT JOIN Cards c ON ot.CategoryName = 'CARD' AND a.OwnerId = c.Id
    LEFT JOIN Stages s ON c.StageId = s.Id
    LEFT JOIN Boards b ON (ot.CategoryName = 'BOARD' AND a.OwnerId = b.Id) 
        OR (ot.CategoryName = 'CARD' AND s.BoardId = b.Id)
    LEFT JOIN Workspaces w ON b.WorkspaceId = w.Id 
        OR (ot.CategoryName = 'WORKSPACE' AND a.OwnerId = w.Id)
WHERE 
    -- User has access through board membership
    (mot.CategoryName = 'BOARD' AND (
        (ot.CategoryName = 'BOARD' AND m.OwnerId = a.OwnerId) OR
        (ot.CategoryName = 'CARD' AND m.OwnerId = s.BoardId)
    ))
    OR
    -- User has access through workspace membership
    (mot.CategoryName = 'WORKSPACE' AND (
        (ot.CategoryName = 'WORKSPACE' AND m.OwnerId = a.OwnerId) OR
        (ot.CategoryName = 'BOARD' AND m.OwnerId = b.WorkspaceId) OR
        (ot.CategoryName = 'CARD' AND m.OwnerId = w.Id)
    ))
    OR
    -- User is directly assigned to the card
    (mot.CategoryName = 'CARD' AND ot.CategoryName = 'CARD' AND m.OwnerId = a.OwnerId)
ORDER BY a.CreatedAt DESC;

-- 61. Get recently viewed Board which user is a member
SELECT TOP 8
    b.BoardName AS BoardName,
    w.WorkspaceName AS WorkspaceName, 
    CASE 
        WHEN b.BoardStatus = 'TEMPLATE' THEN 1
        ELSE 0
    END AS IsTemplate
FROM Boards b
    JOIN UserViewHistories uvh ON uvh.OwnerId = b.Id
    JOIN Workspaces w ON w.Id = b.WorkspaceId
WHERE uvh.UserID = 2;

-- -----------------------------------------------------------------------------
-- SCREEN 30: NOTIFICATION (Slide 43)
-- -----------------------------------------------------------------------------

-- 62. Get all notification of specific User
SELECT 
    a.ActivityDescription,
    a.CreatedAt,
    n.IsRead AS NotificationStatus
FROM Notifications n
    JOIN Activities a ON a.Id = n.ActivityId
WHERE a.UserId = 5;

-- -----------------------------------------------------------------------------
-- SCREEN 31: BOARD COLLECTION (Slide 45)
-- -----------------------------------------------------------------------------

-- 63. Get all Collection and Board belong to Collections in specific Workspace
SELECT 
    b.BoardName AS BoardName,
    b.BackgroundUrl,
    c.CollectionName AS CollectionName
FROM Boards b
    JOIN BoardCollections bc ON bc.BoardId = b.Id
    JOIN Collections c ON c.Id = bc.CollectionId
WHERE c.WorkspaceId = 50;

-- 63. Add Board to Collection
INSERT INTO BoardCollections (BoardId, CollectionId) 
VALUES (1, 5);

