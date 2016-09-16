-- Create RolePermission.

CREATE TABLE `RolePermission` (
    `Id` CHAR(36) NOT NULL,
    `RoleId` CHAR(36) NOT NULL,
    `PermissionId` CHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);

--//@UNDO

DROP TABLE `RolePermission`;
