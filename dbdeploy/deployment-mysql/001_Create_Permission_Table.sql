-- Create Permission

CREATE TABLE [dbo].[Permission](
    [Id] UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    [Name] NVARCHAR(50) NULL,
    [Description] NVARCHAR(50) NULL,
);

--//@UNDO

DROP TABLE [Permission];
