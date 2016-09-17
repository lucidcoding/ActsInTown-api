-- Alter Spot

ALTER TABLE `User` ADD COLUMN `StageName` CHAR(50) NOT NULL;

--//@UNDO

ALTER TABLE `User` DROP COLUMN `StageName`;
