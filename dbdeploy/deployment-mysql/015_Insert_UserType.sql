-- Insert UserType

ALTER TABLE `UserType` CHANGE `Description` `Description` CHAR(100);

INSERT INTO `UserType` (`Id`, `Name`, `Description`, `Order`, `Active`) VALUES ('C1D98234-053B-4426-BE24-73B46904B59A', 'Act', 'I am an act looking for spots for myself', 1, 1);
INSERT INTO `UserType` (`Id`, `Name`, `Description`, `Order`, `Active`) VALUES ('2558EF46-F737-4FCD-9EC3-1D221F241876', 'Agent', 'I am an agent looking for spots for acts I represent', 2, 1);
INSERT INTO `UserType` (`Id`, `Name`, `Description`, `Order`, `Active`) VALUES ('F3DF2CC0-C59A-4B9D-B8E4-CBAB0F71DB49', 'Promoter', 'I am a promoter looking for acts', 3, 1);
INSERT INTO `UserType` (`Id`, `Name`, `Description`, `Order`, `Active`) VALUES ('CE5E24D2-1874-427C-8CAC-E2A57D8C6555', 'Venue', 'I am the owner or representative of a venue looking for acts', 4, 1);

--//@UNDO

DELETE FROM `UserType`;
