-- -----------------------------------------------------------------------------
-- SCREEN 1: TAB BOARDS (SLIDE 4)
-- -----------------------------------------------------------------------------
-- Query Starred Board By User
select b.Id,b.BoardName,b.BackgroundUrl
from UserStarredBoard usb
join Board b on b.Id=usb.BoardId
where usb.UserId=1
order by usb.CreatedAt desc
-- 1. Query 4 suggested templates by Template Category
SELECT TOP 4 
    t.Id as TemplateId,
    t.Title, 
    t.BackgroundUrl
FROM Template t
    JOIN TemplateCategory c ON t.CategoryId = c.Id
WHERE c.CategoryValue = 'User'; -- ':templateCategory'

-- 2. Query 4 recently accessed Board by user
SELECT TOP 4 
    b.Id as BoardId,
    b.BoardName,
    b.BackgroundUrl
FROM Board b
    JOIN UserViewHistory uvh ON uvh.OwnerId  = b.Id
    JOIN OwnerType owt on owt.Id=uvh.OwnerTypeId and owt.OwnerTypeValue='board'
    JOIN Users u ON u.Id = uvh.UserId
WHERE u.Id = 1 -- userId
ORDER BY uvh.AccessedAt DESC;

-- 3. Query all Workspace where User is a Member
SELECT 
    w.Id, 
    w.WorkspaceName
FROM Workspace w
    JOIN Members m ON m.OwnerId = w.Id
    JOIN OwnerType owt ON m.OwnerTypeId = owt.Id
WHERE owt.OwnerTypeValue = 'workspace' 
    AND m.UserId = 1;
    
-- 4. Query all Workspace where User is a Member. 
--    For each workspace, get all Board where User is also a Member
SELECT 
    w.Id AS WorkspaceId,
    w.WorkspaceName,
    w.LogoUrl AS WorkspaceIcon,
    b.Id AS BoardId,
    b.BoardName AS BoardName,
    b.BoardDescription,
    b.BackgroundUrl,
    b.CreatedAt
FROM Workspace w
    -- Find Workspace where User is a Member
    JOIN Members mw ON mw.OwnerId = w.Id
    JOIN OwnerType otw ON otw.Id = mw.OwnerTypeId 
        AND otw.OwnerTypeValue = 'workspace'
    -- Find Board corresponding to Workspace where User is also a Member
    JOIN Board b ON b.WorkspaceId = w.Id
    JOIN Members mb ON mb.OwnerId = b.Id
    JOIN OwnerType otb ON otb.Id = mb.OwnerTypeId 
        AND otb.OwnerTypeValue = 'board'
WHERE mb.UserId = 1
    

-- 5. Query all closed boards where user is a member
SELECT 
    b.Id as BoardID,
    b.BoardName, 
    w.Id as WorkspaceId,
    w.WorkspaceName
FROM Board b
    JOIN Workspace w ON w.Id = b.WorkspaceId
    JOIN Members m ON m.OwnerId = b.Id
    JOIN OwnerType owt ON m.OwnerTypeId = owt.Id 
        AND owt.OwnerTypeValue = 'board'
WHERE m.UserId = 3
    AND b.BoardStatus = 'CLOSED';
--6. Delete Or Reopen a closed board
DECLARE @BoardId INT
UPDATE Board
SET BoardStatus='CLOSED' -- or  'ACTIVE'
WHERE Id=3

-- -----------------------------------------------------------------------------
-- SCREEN 2: TEMPLATES TAB (SLIDE 5)
-- -----------------------------------------------------------------------------

-- 7. Get top 14 template categories
Select Top 7
    tpc.Id,tpc.DisplayValue,tpc.IconUrl
from TemplateCategory tpc


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
FROM Template t
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
FROM Template t
    JOIN Board b ON b.Id = t.BoardId
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
    FROM Board b
    WHERE b.Id=@BoardId
    --Get Stage in Board
    SELECT s.Id as StageId, s.Title as StageTitle,s.Position as StagePosition,c.ColorName as BackgroundColor
    FROM Stage s
    JOIN Color c on c.Id=s.ColorId
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
    JOIN Stage s on s.Id=c.StageId
    LEFT JOIN Color color on TRY_CAST(CASE WHEN c.CardCoverTypeId = 60 THEN c.CoverValue ELSE NULL END AS INT) = color.Id
    LEFT JOIN Attachment a on TRY_CAST(CASE WHEN c.CardCoverTypeId = 61 THEN c.CoverValue ELSE NULL END AS INT) = a.Id
    where s.BoardId=@BoardId
    order by StageId
END
GO
EXEC GetBoardDetail @BoardId=1

-- -----------------------------------------------------------------------------
-- SCREEN 4: CREATE workspace (SLIDE 7)
-- -----------------------------------------------------------------------------

-- 10. Insert data into Workspace
INSERT INTO Workspace (WorkspaceName, WorkspaceDescription, TypeId) 
VALUES ('Quan', 'BBV-YPP4', 'ENGINEERING_IT');

-- -----------------------------------------------------------------------------
-- SCREEN 5: TAB BOARDS IN workspace WITH USERID = 1 (SLIDE 8)
-- -----------------------------------------------------------------------------

-- 11. Get Workspace Name, SettingKey='visibility' and SettingValue 
--     related to SettingKey of Workspace
WITH SettingValueForWorkspace AS (
    SELECT 
        sv.Id as SettingValueId,
        sv.OwnerId,
        sk.Id,
        sk.OwnerTypeId,
        sk.KeyName,
        so.DisplayValue 
    FROM SettingValue sv
        JOIN SettingKey sk ON sk.Id = sv.SettingKeyId
        LEFT JOIN SettingOption so ON sv.SettingContent= so.Id 
            AND sk.IsBoolean=0
        JOIN OwnerType owt ON owt.Id = sk.OwnerTypeId 
            AND owt.OwnerTypeValue = 'workspace'
    WHERE sk.KeyName = 'visibility'
)
SELECT 
    w.Id as WorkspaceId,
    w.WorkspaceName,
    svfw.DisplayValue as Visibility
FROM Workspace w
    JOIN SettingValueForWorkspace svfw ON svfw.OwnerId = w.Id
WHERE w.Id = 2;

-- 12. Get 4 suggested Board by Template Category Type 
SELECT TOP 4 
    b.Id,
    b.BoardName,
    b.BackgroundUrl
FROM Board b
    JOIN Template t ON t.BoardId = b.Id
    JOIN TemplateCategory tc ON t.CategoryId = tc.Id
WHERE tc.CategoryValue = 'operator'
ORDER BY 
    t.Viewed DESC, 
    t.Copied DESC;

-- 13. Get "Your boards" section: Get Board belonging to Workspace 
--     where User is also a Member of the Board
SELECT 
    b.Id as BoardId,
    b.BoardName,
    b.BackgroundUrl
FROM Board b
    JOIN Members m ON m.OwnerId = b.Id
    JOIN Workspace w ON w.Id = b.WorkspaceId
    JOIN OwnerType owt ON owt.Id = m.OwnerTypeId 
        AND owt.OwnerTypeValue = 'board'
WHERE w.Id = 1 
    

-- -----------------------------------------------------------------------------
-- SCREEN 6: MEMBER TAB OF workspace (SLIDE 10)
-- -----------------------------------------------------------------------------

-- 14. Get all Members in Workspace, number of Board in Workspace that Member 
--     participates in and corresponding RolePermissions in Workspace
WITH WorkspaceMembers AS (
    SELECT 
        m.UserId, 
        m.RolePermissonId
    FROM Members m
        JOIN OwnerType owt ON owt.Id = m.OwnerTypeId
    WHERE owt.OwnerTypeValue = 'workspace' 
        AND m.OwnerId = 1
),
BoardInWorkspace AS (
    SELECT 
        b.Id AS BoardId,
        b.BoardName as BoardName,
        b.BackgroundUrl
    FROM Board b
    WHERE b.WorkspaceId = 2
),
BoardMembers AS (
    SELECT 
        m.UserId,
        m.OwnerId AS BoardId
    FROM Members m
        JOIN OwnerType owt ON owt.Id = m.OwnerTypeId
    WHERE owt.OwnerTypeValue = 'board'
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
    JOIN RolePermission p ON wm.RolePermissonId = p.Id
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
FROM ShareLink sl
    JOIN Workspace w ON w.Id = sl.OwnerId
    JOIN OwnerType owt on owt.Id=sl.OwnerTypeId AND owt.OwnerTypeValue='workspace'
    JOIN RolePermission p ON sl.RolePermissonId = p.Id
WHERE w.Id = 1;

-- 16. Update ShareLink Status of Workspace
UPDATE ShareLink 
SET ShareLinkStatus = 1
WHERE OwnerId = 1
    AND OwnerTypeId IN (
        SELECT Id
        FROM OwnerType
        WHERE OwnerTypeValue = 'workspace'
    );

-- -----------------------------------------------------------------------------
-- SCREEN 7: GUEST TAB IN workspace MEMBER
-- -----------------------------------------------------------------------------

--17. Query Members of Board belonging to Workspace, but Members who are not part of the Workspace
WITH WorkspaceBoardMembers AS (
    SELECT 
        m.UserId,
        b.Id AS BoardId,
        b.WorkspaceId
    FROM Members m 
        JOIN Board b ON b.Id = m.OwnerId
        JOIN OwnerType owt ON owt.Id = m.OwnerTypeId 
            AND owt.OwnerTypeValue = 'board'
    WHERE b.WorkspaceId = 3
),
WorkspaceMembers AS (
    SELECT m.UserId
    FROM Members m
        JOIN Workspace w ON w.Id = m.OwnerId
        JOIN OwnerType owt ON owt.Id = m.OwnerTypeId 
            AND owt.OwnerTypeValue = 'workspace'
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
-- SCREEN 8: SHARE board (Slide 12)
-- -----------------------------------------------------------------------------

-- 18. Add Member to Board with Permission
INSERT INTO Members (UserId, RolePermissonId, OwnerTypeId, OwnerId, InvitedBy, JoinedAt, MemberStatus) 
VALUES (1001, 1, 3, 3, 1, '', 'ACTIVE');

-- 19. Create ShareLink for Board with Permission (Each Board has only 1 ShareLink)
IF NOT EXISTS (
    SELECT 1 
    FROM ShareLink sl 
        JOIN OwnerType owt ON owt.Id = sl.OwnerTypeId 
            AND owt.OwnerTypeValue = 'board'
    WHERE sl.OwnerId = 1
)
BEGIN 
    INSERT INTO ShareLink (OwnerTypeId, OwnerId, RolePermissonId, ShareLinkToken, ShareLinkStatus) 
    VALUES (3, 1, 1, '/path', 1);
END
ELSE
BEGIN
    PRINT 'Record already exists, skipping insert.';
END;

--20. Update Status and Permission of ShareLink
UPDATE ShareLink
SET ShareLinkStatus = 'ENABLED',        
    RolePermissonId = 2           
WHERE OwnerId = 1
    AND OwnerTypeId IN (
        SELECT Id 
        FROM OwnerType 
        WHERE OwnerTypeValue = 'board'
    );

--21. Get all Members of Board and their RolePermissions
SELECT 
    m.Id, 
    u.Username,
    p.PermissionName
FROM Members m
    JOIN Board b ON b.Id = m.OwnerId
    JOIN RolePermission p ON p.Id = m.RolePermissonId
    JOIN Users u ON u.Id = m.UserId
WHERE b.Id = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 9: workspace SETTING TAB (Slide 14)
-- -----------------------------------------------------------------------------

-- 22. SettingKey and corresponding SettingOption for Workspace
SELECT 
    sk.KeyName, 
    so.DisplayValue
FROM SettingKey sk
    JOIN SettingKeySettingOption skso ON skso.SettingKeyId = sk.Id
    JOIN SettingOption so ON so.Id = skso.SettingOptionId
    JOIN OwnerType owt ON owt.Id = sk.OwnerTypeId
        AND owt.OwnerTypeValue = 'workspace';

-- 23. SettingValue of specific Workspace

SELECT 
    sk.KeyName,
    sk.OwnerTypeId,
    sv.SettingContent,
    so.DisplayValue
FROM SettingValue sv
    JOIN SettingKey sk ON sk.Id = sv.SettingKeyId
    JOIN OwnerType owt on owt.Id=sk.OwnerTypeId
    LEFT JOIN SettingOption so ON so.Id = sv.SettingContent AND sk.IsBoolean=0
    
WHERE sv.OwnerId = 1 AND owt.OwnerTypeValue='workspace';

-- -----------------------------------------------------------------------------
-- SCREEN 10: board SETTINGS (Slide 15)
-- -----------------------------------------------------------------------------

-- 24. SettingKey with KeyName='permission.*' and corresponding SettingOption for Board
SELECT
    sk.Id as SettingKeyId,
    sk.KeyName,
    sk.OwnerTypeId,
    so.DisplayValue
FROM SettingKey sk
    JOIN SettingKeySettingOption skso ON skso.SettingKeyId = sk.Id
    LEFT JOIN SettingOption so ON so.Id = skso.SettingOptionId AND sk.OwnerTypeId=7
    JOIN OwnerType owt ON owt.Id = sk.OwnerTypeId 
        AND owt.OwnerTypeValue = 'board'
WHERE sk.KeyName LIKE 'permissions.%';

-- 25. SettingKey and corresponding SettingOption for Board
SELECT 
    sk.Id as SettingKeyId,
    sk.KeyName,
    sk.OwnerTypeId,
    so.DisplayValue
FROM SettingKey sk
    JOIN SettingKeySettingOption skso ON skso.SettingKeyId = sk.Id
    LEFT JOIN SettingOption so ON so.Id = skso.SettingOptionId AND sk.OwnerTypeId=7
    JOIN OwnerType owt ON owt.Id = sk.OwnerTypeId 
        AND owt.OwnerTypeValue = 'board';

-- 26. SettingValue corresponding to Board
SELECT
    sv.Id as SettingValueId,
    sv.OwnerId,
    sk.KeyName,
    sk.OwnerTypeId,
    sv.SettingContent,
    so.DisplayValue
FROM SettingValue sv
    JOIN SettingKey sk ON sk.Id = sv.SettingKeyId
    LEFT JOIN SettingOption so ON so.Id = sv.SettingContent AND sk.OwnerTypeId=7
    JOIN OwnerType owt ON owt.Id = sk.OwnerTypeId 
        AND owt.OwnerTypeValue = 'board'
WHERE sv.OwnerId = 3;

-- -----------------------------------------------------------------------------
-- SCREEN 11: workspace POWER-UP (Slide 17)
-- -----------------------------------------------------------------------------

-- 27. Query how many Board in Workspace have Power-Ups added
WITH BoardInWorkspace AS (
    SELECT 
        b.Id AS BoardId,
        b.WorkspaceId
    FROM Board b
    WHERE b.WorkspaceId = 1
),
PowerUpInBoards AS (
    SELECT 
        pu.Id AS PowerUpId,
        pu.PowerUpName,
        pu.IconUrl,
        b.Id AS BoardId
    FROM PowerUp pu
        JOIN BoardPowerUp bpu ON bpu.PowerUpId = pu.Id
        JOIN Board b ON b.Id = bpu.BoardId
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
    puc.DisplayValue AS CategoryName
FROM PowerUp pu
    JOIN PowerUpCategory puc ON puc.Id = pu.CategoryId
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
FROM BillingPlan bp;

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
FROM Subscription s
    JOIN BillingPlan bp ON bp.Id = s.BillingPlanId
    JOIN BillingContact bc ON s.BillingContactId = bc.Id
    JOIN SettingOption so ON so.Id = bc.BillingLanguage
WHERE bc.WorkspaceId = 1;

--31. Change Payment Information
UPDATE PaymentInformation
SET 
    CardNumber = '4628151718263',
    CardBrand = 'VISA',
    ExpirationDate = '24/12/2025',
    CVV = '247',
    Country = 'Vietnam'
WHERE BillingContactId = 1;

--32. Change BillingContact Information
UPDATE BillingContact
SET 
    BillingContactName = 'Quan Phan', 
    BillingContactEmail = 'huyhoangnguyen1002@gmail.com'
WHERE WorkspaceId = 1;
-- -----------------------------------------------------------------------------
-- SCREEN 15: BILLING HISTORY (Slide 22)
-- -----------------------------------------------------------------------------
-- 33. Change Additional invoice details
UPDATE BillingContact
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
FROM Subscription s
    JOIN BillingPlan bp ON bp.Id = s.BillingPlanId
    JOIN BillingContact bc ON bc.Id = s.BillingContactId
WHERE bc.WorkspaceId = 1;

-- -----------------------------------------------------------------------------
-- SCREEN 16: EXPORT workspace  (Slide 24)
-- -----------------------------------------------------------------------------

--35. Create Export for Workspace
INSERT INTO Export (WorkspaceId, CreatedBy, CreatedAt, Size) 
VALUES (1, 1, '', 255);

--36. Query Export from specific Workspace
SELECT 
    e.Id as ExportId,
    e.CreatedAt,
    e.Size
FROM Export e
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
-- SCREEN 18: board SCREEN (Slide 26)
-- -----------------------------------------------------------------------------

--39. Create Board
INSERT INTO Board (BoardName, BoardDescription, CreatedAt, CreatedBy, BackgroundUrl, WorkspaceId, BoardStatus) 
VALUES ('bbv-VietNam', '', '', 1, '', 0, 'image.png', 1, 'ACTIVE');

--40. Create Stage in Board
INSERT INTO Stage (Title, CreatedAt, BoardId, StageStatus, Position)
VALUES ('bbv', '', 1, 'ACTIVE', 1);

-- -----------------------------------------------------------------------------
-- SCREEN 19: STAGE POSITION (Slide 27)
-- -----------------------------------------------------------------------------

--41. Update Stage Position
UPDATE Stage
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
        LEFT JOIN Attachment a ON a.CardId = c.Id
    WHERE c.StageId = 1
    GROUP BY c.Id
),
LabelAgg AS (
    SELECT
        cl.CardId,
        STRING_AGG(l.Title, ', ') WITHIN GROUP (ORDER BY l.Title) AS Labels
    FROM CardLabel cl
        JOIN Labels l ON l.Id = cl.LabelId
    GROUP BY cl.CardId
),
MemberAgg AS (
    SELECT
        m.OwnerId AS CardId,
        STRING_AGG(u.Id, ', ') AS AssignedMemberId,
        STRING_AGG(u.Username, ', ') WITHIN GROUP (ORDER BY u.Username) AS AssignedMembers
    FROM Members m
        JOIN OwnerType owt ON owt.Id = m.OwnerTypeId 
            AND owt.OwnerTypeValue = 'CARD'
        JOIN Users u ON u.Id = m.UserId
    GROUP BY m.OwnerId
)
SELECT
    c.Id,
    c.Title,
    c.CardCoverTypeId,
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
    JOIN Stage s ON c.StageId = s.Id
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
        JOIN OwnerType owt ON owt.Id = m.OwnerTypeId 
            AND owt.OwnerTypeValue = 'CARD'
        JOIN Users u ON u.Id = m.UserId
    GROUP BY m.OwnerId
),
LabelAgg AS (
    SELECT
        cl.CardId,
        STRING_AGG(l.Id, ', ') WITHIN GROUP (ORDER BY l.Id) AS LabelId,
        STRING_AGG(l.Title, ', ')  AS LabelTitle
    FROM CardLabel cl
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
FROM Comment comment
    JOIN Cards c ON c.Id = comment.CardId
WHERE c.Id = 1;

--45. Query Activities related to Card
SELECT 
    u.Username,
    a.ActivityDescription,
    a.CreatedAt
FROM Activity a
    JOIN Users u ON u.Id = a.UserId
    JOIN OwnerType owt ON owt.Id = a.CategoryId 
        AND owt.OwnerTypeValue = 'card'
WHERE a.OwnerId = 1;
GO

--46. Get MovedCard Data (Store Procedure)
CREATE OR ALTER PROCEDURE sp_GetMoveCardData
    @UserId INT,
    @SelectedBoardId INT = NULL,
    @SelectedListId INT = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Get Board User is Member
    SELECT
        b.Id AS BoardId,
        b.BoardName AS BoardName
    FROM Board b
        JOIN Members m ON m.OwnerId = b.Id
    WHERE m.UserId = @UserId;
    
    -- Get Stage in Board
    IF @SelectedBoardId IS NOT NULL
    BEGIN
        SELECT
            s.Id AS StageId,
            s.Title AS StageTitle
        FROM Stage s
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
    JOIN Stage s ON s.Id = c.StageId
    JOIN Board b ON s.BoardId = b.Id
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
    CardCoverTypeId = 2, --Color
    CoverValue = '5'
WHERE Id = 1;

-- Update Card Cover to Unsplash Image
UPDATE Cards
SET 
    CardCoverTypeId= 1, --Unsplash
    CoverValue = 'unsplashimage.png'
WHERE Id = 1;

-- Update Card Cover to Attachment
UPDATE Cards
SET 
    CardCoverTypeId = 3, 
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
    LEFT JOIN Color c ON l.ColorId = c.Id
    LEFT JOIN CardLabel cl ON l.Id = cl.LabelId AND cl.CardId = 10
ORDER BY IsChecked DESC;

-- 51. Get all Color 
SELECT TOP 10
    c.ColorName,
    c.Icon
FROM Color c;
GO

-- -----------------------------------------------------------------------------
-- SCREEN 25: CHECKLIST AND CHECKLIST ITEM (Slide 34)
-- -----------------------------------------------------------------------------

-- 52. Query all CheckList in specific Card
SELECT 
    cl.Id AS CheckListId,
    cl.CheckListName AS CheckListName
FROM CheckList cl
WHERE cl.CardId = 1;

-- 53. Query all CheckListItem in CheckList of specific Card
SELECT 
    cli.CheckListItemName AS ItemName,
    cli.DueDate AS DueDate,
    m.Id AS MemberId,
    u.Username AS Username,
    cli.CheckListItemStatus AS IsCompleteItem,
    cli.CheckListId AS CheckListID
FROM CheckListItem cli
    JOIN CheckList cl ON cl.Id = cli.CheckListId
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
    a.AttachmentTypeId AS AttachmentFileType,
    a.AttachmentName,
    a.CreatedAt
FROM Attachment a
    JOIN Cards c ON a.CardId = c.Id
WHERE c.Id = 1;
GO

-- -----------------------------------------------------------------------------
-- SCREEN 27: CUSTOM FIELD (Slide 38)
-- -----------------------------------------------------------------------------

-- 55. Query All CustomField, FieldItem of Board (store procedure)
CREATE OR ALTER PROCEDURE sp_GetAllCustomFieldAndFieldValue
    @BoardId INT
AS
BEGIN
    -- Query all CustomField and FieldItem if have dropdown CustomField been add to Board
    SELECT 
        cf.Id,
        cf.Title,
        cf.Position,
        dtt.DataTypeValue
    FROM CustomField cf
    JOIN DataType dtt ON dtt.Id=cf.DataTypeId
    WHERE cf.BoardId = @BoardId;

    SELECT
        cf.Id as CustomFieldId,
        fi.Id as FieldItemId,
        fi.FieldItemValue AS FieldItemValue,
        fi.Position AS FieldItemPriority,
        c.ColorName AS Color,
        c.Icon AS ColorIcon
    FROM FieldItem fi
        JOIN CustomField cf ON cf.Id = fi.CustomFieldId 
        JOIN DataType dtt on dtt.Id=cf.DataTypeId and dtt.DataTypeValue='dropdown'
        LEFT JOIN Color c ON fi.ColorId=c.Id
    WHERE cf.BoardId = @BoardId;
END;
GO

-- Execute stored procedure example
EXEC sp_GetAllCustomFieldAndFieldValue @BoardId = 4;

-- 56. Query CustomField and value of all Cards in Board except dropdown type
DECLARE @cols NVARCHAR(MAX)
DECLARE @query NVARCHAR(MAX)


SELECT @cols = STUFF((
    SELECT DISTINCT ',' + QUOTENAME(cf.Title)
    FROM CustomField cf
    WHERE cf.BoardId = 1 and cf.DataTypeId <> 2
    FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, '')


SET @query = '
SELECT 
    CardId,
    CardTitle,
    ' + @cols + '
FROM (
    SELECT 
        c.Id AS CardId,
        c.Title AS CardTitle,
        cf.Title AS CustomFieldTitle,
        fv.FieldValue
    FROM Cards c
        INNER JOIN Stage s ON c.StageId = s.Id
        INNER JOIN Board b ON s.BoardId = b.Id
        LEFT JOIN FieldValue fv ON c.Id = fv.CardId
        LEFT JOIN CustomField cf ON fv.CustomFieldId = cf.Id AND cf.BoardId = 1
    Where b.Id=1 and cf.DataTypeId <> 7
) AS SourceTable
PIVOT (
    MAX(FieldValue)
    FOR CustomFieldTitle IN (' + @cols + ')
) AS PivotTable
ORDER BY CardId'

-- Thực thi câu query động
EXEC(@query)

-- -----------------------------------------------------------------------------
-- SCREEN 28: CARD STICKER (Slide 40)
-- -----------------------------------------------------------------------------

-- 57. Query all Sticker
SELECT 
    st.Id, 
    st.StickerName,
    st.StickerUrl
FROM Sticker st;

-- 58. Add Sticker to Card
INSERT INTO CardSticker (CardId, StickerId, PositionX, PositionY) 
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
FROM Sticker s
    JOIN CardSticker cs ON cs.StickerId = s.Id
    JOIN Cards c ON c.Id = cs.CardId
    JOIN Stage sta ON sta.Id = c.StageId
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
        WHEN owt.OwnerTypeValue = 'board' THEN 'Board activity'
        WHEN owt.OwnerTypeValue = 'CARD' THEN 'Card: ' + c.Title
        WHEN owt.OwnerTypeValue = 'workspace' THEN 'Workspace activity'
        ELSE owt.OwnerTypeValue
    END AS ActivityDescription,
    owt.OwnerTypeValue AS ActivityType
FROM Activity a
    INNER JOIN Users u ON a.UserId = u.Id
    INNER JOIN OwnerType owt ON a.CategoryId = owt.Id
    -- Get all memberships for the current user
    INNER JOIN Members m ON m.UserId = 3
    INNER JOIN OwnerType mot ON m.OwnerTypeId = mot.Id
    -- Context joins
    LEFT JOIN Cards c ON owt.OwnerTypeValue = 'CARD' AND a.OwnerId = c.Id
    LEFT JOIN Stage s ON c.StageId = s.Id
    LEFT JOIN Board b ON (owt.OwnerTypeValue = 'board' AND a.OwnerId = b.Id) 
        OR (owt.OwnerTypeValue = 'CARD' AND s.BoardId = b.Id)
    LEFT JOIN Workspace w ON b.WorkspaceId = w.Id 
        OR (owt.OwnerTypeValue = 'workspace' AND a.OwnerId = w.Id)
WHERE 
    -- User has access through board membership
    (mot.OwnerTypeValue = 'board' AND (
        (owt.OwnerTypeValue = 'board' AND m.OwnerId = a.OwnerId) OR
        (owt.OwnerTypeValue = 'CARD' AND m.OwnerId = s.BoardId)
    ))
    OR
    -- User has access through workspace membership
    (mot.OwnerTypeValue = 'workspace' AND (
        (owt.OwnerTypeValue = 'workspace' AND m.OwnerId = a.OwnerId) OR
        (owt.OwnerTypeValue = 'board' AND m.OwnerId = b.WorkspaceId) OR
        (owt.OwnerTypeValue = 'CARD' AND m.OwnerId = w.Id)
    ))
    OR
    -- User is directly assigned to the card
    (mot.OwnerTypeValue = 'CARD' AND owt.OwnerTypeValue = 'CARD' AND m.OwnerId = a.OwnerId)
ORDER BY a.CreatedAt DESC;

-- 61. Get recently viewed Board which user is a member
SELECT TOP 8
    b.BoardName AS BoardName,
    w.WorkspaceName AS WorkspaceName, 
    CASE 
        WHEN b.BoardStatus = 'TEMPLATE' THEN 1
        ELSE 0
    END AS IsTemplate
FROM Board b
    JOIN UserViewHistory uvh ON uvh.OwnerId = b.Id
    JOIN Workspace w ON w.Id = b.WorkspaceId
WHERE uvh.UserID = 2;

-- -----------------------------------------------------------------------------
-- SCREEN 30: NOTIFICATION (Slide 43)
-- -----------------------------------------------------------------------------

-- 62. Get all notification of specific User
SELECT 
    a.ActivityDescription,
    a.CreatedAt,
    n.IsRead AS NotificationStatus
FROM Notification n
    JOIN Activity a ON a.Id = n.ActivityId
WHERE a.UserId = 5;

-- -----------------------------------------------------------------------------
-- SCREEN 31: board COLLECTION (Slide 45)
-- -----------------------------------------------------------------------------

-- 63. Get all Collection and Board belong to Collections in specific Workspace
SELECT 
    b.BoardName AS BoardName,
    b.BackgroundUrl,
    c.CollectionName AS CollectionName
FROM Board b
    JOIN BoardCollection bc ON bc.BoardId = b.Id
    JOIN Collections c ON c.Id = bc.CollectionId
WHERE c.WorkspaceId = 50;

-- 63. Add Board to Collection
INSERT INTO BoardCollection (BoardId, CollectionId) 
VALUES (1, 5);

