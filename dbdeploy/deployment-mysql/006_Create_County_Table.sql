-- Create County

CREATE TABLE `County` (
    `Id` CHAR(36) NOT NULL,
    `Name` CHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);

--//@UNDO

DROP TABLE `County`;
