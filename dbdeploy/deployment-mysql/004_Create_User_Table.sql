-- Create User

CREATE TABLE `User` (
    `Id` CHAR(36) NOT NULL,
    `Username` CHAR(50) NOT NULL,
    `Password` CHAR(100) NOT NULL,
    `PasswordSalt` CHAR(100) NOT NULL,
    `FirstName` CHAR(50) NOT NULL,
    `LastName` CHAR(50) NOT NULL,
    `Email` CHAR(50) NOT NULL,
    `Enabled` BIT NOT NULL,
    `RoleId` CHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);

--//@UNDO

DROP TABLE `User`;
