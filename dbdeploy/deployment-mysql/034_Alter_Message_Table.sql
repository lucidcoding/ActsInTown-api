-- Alter Spot

ALTER TABLE `Message` ADD COLUMN `Body` NVARCHAR(255) NOT NULL;

--//@UNDO

ALTER TABLE `Message` DROP COLUMN `Body`;
