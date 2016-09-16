-- Create Spot

CREATE TABLE `Spot` (
    `Id` CHAR(36) NOT NULL,
    `UserId` CHAR(36) NOT NULL,
    `ScheduledFor` DATETIME NOT NULL,
    `DurationMinutes` INT NOT NULL,
    `TownId` CHAR(36) NOT NULL,
    `VanueName` CHAR(50) NOT NULL,
    `AddedOn` CHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);

--//@UNDO

DROP TABLE `Spot`;
