-- Alter Spot

ALTER TABLE `Spot` ADD COLUMN `BookedState` INT NOT NULL DEFAULT 0;

--//@UNDO

ALTER TABLE `Spot` DROP COLUMN `BookedState`;
