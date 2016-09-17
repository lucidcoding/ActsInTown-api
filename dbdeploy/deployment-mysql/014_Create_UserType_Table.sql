-- Create UserType

CREATE TABLE `UserType` (
    `Id` CHAR(36) NOT NULL,
    `Name` CHAR(20) NOT NULL,
    `Description` CHAR(50) NOT NULL,
    `Order` INT NOT NULL,
    `Active` BIT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);

--//@UNDO

DROP TABLE `UserType`;
