-- Alter Spot

ALTER TABLE `Spot` ADD `Description` VARCHAR(250) NULL;

--//@UNDO

ALTER TABLE `Spot` DROP COLUMN `Description`;
