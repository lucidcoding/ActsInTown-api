-- Create Town

CREATE TABLE `Town` (
    `Id` CHAR(36) NOT NULL,
    `Name` CHAR(50) NOT NULL,
    `CountyId` CHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);

--//@UNDO

DROP TABLE `Town`;
