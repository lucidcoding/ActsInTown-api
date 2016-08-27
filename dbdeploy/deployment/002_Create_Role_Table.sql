-- Create Role

CREATE TABLE [dbo].[Role](
    [Id] UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    [Name] NVARCHAR(20) NOT NULL,
    [Description] NVARCHAR(50) NOT NULL
);

--//@UNDO

DROP TABLE [Role];
