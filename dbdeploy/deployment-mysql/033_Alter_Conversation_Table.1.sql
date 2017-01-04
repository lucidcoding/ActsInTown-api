-- Alter Spot

ALTER TABLE `Conversation` ADD COLUMN `UpdatedOn` DATETIME NOT NULL;

--//@UNDO

ALTER TABLE `Conversation` DROP COLUMN `UpdatedOn`;
