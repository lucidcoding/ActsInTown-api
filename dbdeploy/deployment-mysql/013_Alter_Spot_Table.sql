-- Alter Spot

ALTER TABLE `Spot` DROP COLUMN `AddedOn`;
ALTER TABLE `Spot` ADD COLUMN `AddedOn` DATETIME NOT NULL;

--//@UNDO

ALTER TABLE `Spot` DROP COLUMN `AddedOn`;
ALTER TABLE `Spot` ADD COLUMN `AddedOn` CHAR(36) NOT NULL;