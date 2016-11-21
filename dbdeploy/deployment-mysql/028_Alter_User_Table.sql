-- Alter User

ALTER TABLE `User` ADD COLUMN `PasswordResetToken` CHAR(50) NULL;
ALTER TABLE `User` ADD COLUMN `PasswordResetTokenExpiry` DATETIME NULL;

--//@UNDO

ALTER TABLE `User` DROP COLUMN `PasswordResetTokenExpiry`;
ALTER TABLE `User` DROP COLUMN `PasswordResetToken`;
