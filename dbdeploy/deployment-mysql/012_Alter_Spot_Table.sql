-- Create Spot

ALTER TABLE `Spot` CHANGE `VanueName` `VenueId` CHAR(36);

ALTER TABLE `Spot` ADD `Cancelled` BIT NOT NULL DEFAULT 1;

--//@UNDO

ALTER TABLE `Spot` DROP COLUMN `Cancelled`;

ALTER TABLE `Spot` CHANGE `VenueId` `VanueName` CHAR(50);
