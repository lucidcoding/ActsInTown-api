-- Create ConversationUser

CREATE TABLE `ConversationUser` (
    `Id` CHAR(36) NOT NULL,
    `ConversationId` CHAR(36) NOT NULL,
    `UserId` CHAR(36) NOT NULL,
    `JoinedOn` DATETIME NOT NULL,
    PRIMARY KEY (`Id`),
    UNIQUE KEY `Id` (`Id`)
);

--//@UNDO

DROP TABLE `ConversationUser`;
