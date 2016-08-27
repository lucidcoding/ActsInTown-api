-- Create RolePermission.

CREATE TABLE [dbo].[RolePermission](
    [Id] UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    [RoleId] UNIQUEIDENTIFIER NOT NULL,
    [PermissionId] UNIQUEIDENTIFIER NOT NULL
);

--//@UNDO

DROP TABLE [RolePermission];
