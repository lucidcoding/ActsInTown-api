-- Create Permission

CREATE TABLE `Permission` (
    `Id` CHAR(36) NOT NULL,
    `Name` CHAR(50) NULL,
    `Description` CHAR(50) NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);

--//@UNDO

DROP TABLE `Permission`;
