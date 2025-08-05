--screen 1: dashboard & account (Slide 9)
-- 1. Get favorite list from user
select w.WorkspaceName,l.Icon,l.ListName
from List l
join FavoriteList fl on fl.ListId=l.Id
join Workspace w on w.Id=l.WorkspaceId
join Account a on a.Id=fl.FavoredBy
where a.Id=1
-- 2. query recent list from user (cant do for now bc not enough table)
-- 3. query list create by user
select l.ListName,l.Icon , w.WorkspaceName
from List l
join Workspace w on w.Id=l.WorkspaceId
where l.CreatedBy=1

-- screen 2: Account Screen
-- 4. find account by id
select a.FirstName,a.LastName,a.Avatar,a.Email
from Account a
where a.Id=1

-- screen 3: Create List 
-- 5. show list type
select lt.Title,lt.HeaderImage
from ListType lt 

-- screen 4: Template 
-- 6. Show template from a specific organization
select lt.Title,lt.HeaderImage
from ListTemplate lt 
join TemplateProvider tp on tp.Id=lt.ProviderId
where tp.ProviderName='Microsoft'

-- screen 5: ListTemplate Detail
--7. Show ListTemplate detail
select lt.Icon, lt.Title, lt.Sumary, lt.Feature
from ListTemplate lt 
where lt.Id=1

--screen 6: List Detail