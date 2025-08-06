CREATE TABLE CachedListTemplateJson (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    ListTemplateId INT NOT NULL,
    CachedJson NVARCHAR(MAX),
    CachedAt DATETIME NOT NULL DEFAULT GETDATE()
);
Go
CREATE OR ALTER PROCEDURE sp_CacheTemplateJson
    @ListTemplateId INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @json NVARCHAR(MAX);

    SELECT 
        trow.Id AS rowid,
        (
            SELECT 
                tcol.Id AS colid,
                tcol.ColumnName,
                sdt.Icon,
                tcell.CellValue
            FROM TemplateColumn tcol
            INNER JOIN SystemDataType sdt ON tcol.SystemDataTypeId = sdt.Id
            INNER JOIN TemplateSampleCell tcell 
                ON tcol.Id = tcell.TemplateColumnId 
                AND tcell.TemplateSampleRowId = trow.Id
            WHERE tcol.ListTemplateId = @ListTemplateId
            FOR JSON PATH
        ) AS cells
    FROM TemplateSampleRow trow
    WHERE trow.ListTemplateId = @ListTemplateId
    FOR JSON PATH, ROOT('rows');

    
    DELETE FROM CachedListTemplateJson WHERE ListTemplateId = @ListTemplateId;

   
    INSERT INTO CachedListTemplateJson (ListTemplateId, CachedJson)
    VALUES (@ListTemplateId, @json);
END;
Go
EXEC sp_CacheTemplateJson @ListTemplateId = 2

SELECT
        tcol.Id AS colId,
        tcol.ColumnName,
        sdt.Icon,
        trow.Id rowid,
        tcell.CellValue,
        tcol.ListTemplateId
    FROM 
        TemplateColumn tcol
    INNER JOIN
        SystemDataType sdt ON tcol.SystemDataTypeId = sdt.Id
    INNER JOIN
        TemplateSampleRow trow ON tcol.ListTemplateId = trow.ListTemplateId
    INNER JOIN 
        TemplateSampleCell tcell 
            ON tcol.Id = tcell.TemplateColumnId 
            AND trow.Id = tcell.TemplateSampleRowId
    WHERE 
        tcol.ListTemplateId = 1

