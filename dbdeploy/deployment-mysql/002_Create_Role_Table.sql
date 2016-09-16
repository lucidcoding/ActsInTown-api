-- Create Role

CREATE TABLE `Role` (
    `Id` CHAR(36) NOT NULL,
    `Name` CHAR(20) NOT NULL,
    `Description` CHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);

--//@UNDO

DROP TABLE `Role`;
