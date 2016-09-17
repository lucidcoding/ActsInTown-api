-- Create Spot

ALTER TABLE `Spot` CHANGE `VenueId` `VenueName` CHAR(50);

--//@UNDO

ALTER TABLE `Spot` CHANGE `VenueName` `VenueId` CHAR(36);
