-- Create Conversation

CREATE TABLE `Conversation` (
    `Id` CHAR(36) NOT NULL,
    `StartedOn` DATETIME NOT NULL,
    `Deleted` BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (`Id`),
    UNIQUE KEY `Id` (`Id`)
);

--//@UNDO

DROP TABLE `Conversation`;
