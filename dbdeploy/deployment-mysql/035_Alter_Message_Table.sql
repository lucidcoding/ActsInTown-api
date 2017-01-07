-- Alter Spot

ALTER TABLE `Message` DROP COLUMN `ConverstationId`;
ALTER TABLE `Message` ADD COLUMN `ConversationId` CHAR(36) NOT NULL;

--//@UNDO

ALTER TABLE `Message` ADD COLUMN `ConverstationId` CHAR(36) NOT NULL;
ALTER TABLE `Message` DROP COLUMN `ConversationId`;
