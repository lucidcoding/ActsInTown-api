-- Alter Spot

ALTER TABLE `User` MODIFY COLUMN `StageName` CHAR(50) NULL;

--//@UNDO

ALTER TABLE `User` MODIFY COLUMN `StageName` CHAR(50) NOT NULL;
