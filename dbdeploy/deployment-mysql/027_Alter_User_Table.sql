-- Alter User

ALTER TABLE `User` ADD COLUMN `ProfilePictureUploaded` BIT NOT NULL DEFAULT 0;

--//@UNDO

ALTER TABLE `User` DROP COLUMN `ProfilePictureUploaded`;
