-- Screen 1 Slide 4: Tab Boards
-- 1. Query 4 suggested templates by Template Category
SELECT TOP 4 
    t.Title, 
    t.BackgroundUrl
FROM Templates t
    JOIN TemplateCategories tc ON tc.id = t.TemplateCategoryId
WHERE tc.Name = 'Geologist II'; --':templateCategory'

-- 2. Query 4 recently accessed Boards by user
SELECT TOP 4 
    b.Name,
    b.BackgroundUrl
FROM Boards b
    JOIN BoardUsers bu ON bu.BoardId = b.Id
    JOIN Users u ON u.Id = bu.UserID
WHERE u.Id = 2 --userId
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

-- Screen 2 Templates tab Slide 5
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

-- Screen 3 Template detail Slide 6
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
    JOIN Boards b ON b.id = t.BoardId
    JOIN Users u ON t.CreatedBy = u.Id
WHERE t.Id = 1; --templateId

-- Screen 4: Create Workspace Slide 7
-- 9. Insert data into Workspaces
INSERT INTO Workspaces (Name, Description, Type) 
VALUES ('', '', '');

-- Screen 5: Tab Boards in Workspace with userId = 1 Slide 8
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

-- Screen 6: Member tab of Workspace Slide 10
-- 13. Get all Members in Workspace, number of Boards in Workspace that Member participates in and corresponding permissions in Workspace
WITH WorkspaceMembers AS (
    SELECT 
        m.UserId, 
        m.PermissionId
    FROM Members m
        JOIN OwnerTypes ot ON ot.id = m.OwnerTypeId
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

-- Screen 7: Guest Tab in Workspace Member
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

-- Screen 8: Share Board 
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

-- Screen 9: Workspace Setting Tab
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

-- Screen 10: Board Settings
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

-- Screen 11: Workspace PowerUp
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

-- Screen 12: Power-Ups Detail
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

--Screen 13: BillingPlan
-- 28. Get Billing Plan
select 
    bp.Name, 
    bp.PricePerUser, 
    bp.Type
from BillingPlans bp

--Screen 14: Billing when have Subscription
-- 29. Get Subscription of specific Workspace
select 
    bp.Name, 
    s.EndDate,
    bp.PricePerUser,
    s.MemberCountBilled,
    bc.Name,
    bc.Email,
    so.DisplayValue as Language
from Subscriptions s
join BillingPlans bp on bp.Id=s.BillingPlanId
join BillingContacts bc on s.BillingContactId= bc.Id
join SettingOptions so on so.Id=bc.Language
where bc.WorkspaceId=1
--30. Change PaymentInformation
update PaymentInformations 
set 
    CardNumber='4628151718263',
    CardBrand='VISA',
    ExpirationDate='24/12/2025',
    CVV='247',
    Country='Vietnam'
where BillingContactId=1
--31. Change BillingContact Information
update BillingContacts
set Name='Quan Phan', Email='huyhoangnguyen1002@gmail.com'
where WorkspaceId=1
--32. Change Additional invoice details
update BillingContacts
set AdditionalInvoiceDetail='TBD'
where WorkspaceId=1
--33. Query Billing history from Subscription
