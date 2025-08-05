-- screen 1: User Information
-- 1. Get specific User info
select a.Email,a.UserName,a.UserImg
from Account a
where a.UserId=1

-- screen 2: Setting User
-- 2. Get specific user setting
select appsetting.SettingKey,appsetting.SettingValue
from SettingUser su
join AppSetting appsetting on appsetting.SettingId=su.SettingId
where su.SettingUserId=1

--screen 3: Home 
-- 3. full-text search
DECLARE @TextQuery nvarchar(50) = 'for';

WITH TokenizedQuery AS (
    SELECT Term
    FROM dbo.fn_TokenizeText(@TextQuery)
),
AllMatches AS (
    SELECT s.FileContentId, COUNT(*) as MatchingTerms
    FROM SearchIndex s
    JOIN TokenizedQuery tq ON s.Term = tq.Term
    GROUP BY s.FileContentId
    HAVING COUNT(*) > 0
)
SELECT 
    fc.FileId,
	uf.UserFileName,
    fc.ContentChunk,
    SUM(s.Bm25Score) AS TotalBm25Score,
    COUNT(s.Term) AS MatchedTerms,
    (SELECT COUNT(*) FROM TokenizedQuery) AS TotalQueryTerms
FROM AllMatches am
JOIN FileContent fc ON am.FileContentId = fc.ContentId
JOIN SearchIndex s ON s.FileContentId = fc.ContentId
join UserFile uf on fc.FileId = uf.FileId
WHERE s.Term IN (SELECT Term FROM TokenizedQuery)
GROUP BY fc.FileId, fc.ContentChunk, uf.UserFileName
ORDER BY TotalBm25Score DESC;

-- 4. Get Recomment File for a specific Account
DECLARE @CurrentUserId INT = 2; -- current UserId

SELECT 
    uf.FileId,
    uf.UserFileName,
    ar.ActionLog,
    ar.ActionDateTime,
    uf.UserFilePath,
    CASE 
        WHEN uf.OwnerId = @CurrentUserId THEN 'My Drive'
        WHEN EXISTS (
            SELECT 1 
            FROM Share s
            JOIN SharedUser su ON su.ShareId = s.ShareId
            WHERE su.UserId = @CurrentUserId 
              AND s.ObjectId = uf.FileId 
              AND s.ObjectTypeId = (SELECT ObjectTypeId FROM ObjectType WHERE ObjectTypeName = 'file')
        ) THEN 'Được chia sẻ với tôi'
        WHEN f.FolderName IS NOT NULL THEN f.FolderName -- tên folder (như "Hội đồng 3")
        ELSE 'Không xác định'
    END AS [Địa điểm]
FROM UserFile uf
LEFT JOIN Folder f ON uf.FolderId = f.FolderId
JOIN ActionRecent ar on ar.ObjectId=uf.FileId 
    and ar.UserId=@CurrentUserId
    and ar.ActionLog LIKE '%file%'
ORDER BY ar.ActionDateTime DESC


--screen 4: File/folder Activity (Slide 4)
-- 5. Get specific folder/file activities
select a.UserName,a.UserImg,ar.ActionLog,ar.ActionDateTime,uf.UserFileName
from ActionRecent ar
join Account a on a.UserId=ar.UserId
join ObjectType ot on ot.ObjectTypeId=ar.ObjectTypeId -- or 'folder'
join UserFile uf on uf.FileId=ar.ObjectId -- or join Folder f on f.FolderId=ar.ObjectId
where ar.ObjectId=10

-- screen 5: My Drive
-- 6. Show Folder where user login is owner, sort by FolderName
DECLARE @CurrentUserLogin INT =1
select f.FolderId, f.FolderName,c.ColorName
from Folder f
join Color c on c.ColorId=f.ColorId
where f.OwnerId=@CurrentUserLogin
order by f.FolderName asc
-- 7. Show File where user login is owner, sort by File Name
select ft.FileTypeName,ft.Icon,uf.FileId,uf.UserFileName,uf.UserFileThumbNailImg
from UserFile uf
join FileType ft on ft.FileTypeId=uf.FileTypeId
where uf.OwnerId=@CurrentUserLogin
order by uf.UserFileName asc

--screen 6: Share with me
-- 8. Get Folder and File share with current user login, order by share date
select f.FolderId,uf.FileId,f.FolderName,uf.UserFileName,c.ColorName as FolderColor,a.Email as SharerEmail,s.CreatedAt as ShareDate
from Share s
join Account a on a.UserId=s.Sharer
join SharedUser su on s.ShareId=su.ShareId
join ObjectType ot on ot.ObjectTypeId=s.ObjectTypeId
left join Folder f on s.ObjectId=f.FolderId and ot.ObjectTypeId=1
join Color c on c.ColorId=f.ColorId and ot.ObjectTypeId=1
left join UserFile uf on s.ObjectId=uf.FileId and ot.ObjectTypeId=2
where su.UserId=2