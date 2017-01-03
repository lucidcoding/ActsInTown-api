-- Create Message

CREATE TABLE `Message` (
    `Id` CHAR(36) NOT NULL,
    `UserId` CHAR(36) NOT NULL,
    `ConverstationId` CHAR(36) NOT NULL,
    `AddedOn` DATETIME NOT NULL,
    `Deleted` BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (`Id`),
    UNIQUE KEY `Id` (`Id`)
);

--//@UNDO

DROP TABLE `Message`;
