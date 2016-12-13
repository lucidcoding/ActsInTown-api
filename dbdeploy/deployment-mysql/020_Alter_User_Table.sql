-- Alter User

ALTER TABLE `User` ADD COLUMN `VerificationToken` CHAR(50) NOT NULL DEFAULT '';
ALTER TABLE `User` ADD COLUMN `VerificationTokenExpiry` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:01';
ALTER TABLE `User` ADD COLUMN `Verified` BIT NOT NULL DEFAULT 1;

--//@UNDO

ALTER TABLE `User` DROP COLUMN `Verified`;
ALTER TABLE `User` DROP COLUMN `VerificationTokenExpiry`;
ALTER TABLE `User` DROP COLUMN `VerificationToken`;
