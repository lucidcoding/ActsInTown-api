-- Insert UserType

INSERT INTO `Spot` (`Id`, `UserId`, `ScheduledFor`, `DurationMinutes`, `TownId`, `VenueName`, `Cancelled`, `AddedOn`, `BookedState`) 
VALUES ('C1D98234-053B-4426-BE24-73B46904B59A', 'B9A3886F-4120-45C8-B060-AC09A4386859', '2020-10-01 20:00:00', 20, '54533C40-D822-4FAE-B066-F2E0CDF04C52', 'The Comedy Store', 0, '2016-10-01 20:00:00', 0);

INSERT INTO `Spot` (`Id`, `UserId`, `ScheduledFor`, `DurationMinutes`, `TownId`, `VenueName`, `Cancelled`, `AddedOn`, `BookedState`) 
VALUES ('818DE7E4-6593-4957-9CFF-D853089E902C', 'B9A3886F-4120-45C8-B060-AC09A4386859', '2020-10-01 21:00:00', 30, '54533C40-D822-4FAE-B066-F2E0CDF04C52', 'The Comedy Store', 0, '2016-10-01 20:00:00', 0);

INSERT INTO `Spot` (`Id`, `UserId`, `ScheduledFor`, `DurationMinutes`, `TownId`, `VenueName`, `Cancelled`, `AddedOn`, `BookedState`) 
VALUES ('E62F2E5A-704C-42E0-8DDE-59037B1FECA8', 'B9A3886F-4120-45C8-B060-AC09A4386859', '2020-10-02 20:30:00', 10, '54533C40-D822-4FAE-B066-F2E0CDF04C52', 'The Comedy Store', 0, '2016-10-01 20:00:00', 0);

INSERT INTO `Spot` (`Id`, `UserId`, `ScheduledFor`, `DurationMinutes`, `TownId`, `VenueName`, `Cancelled`, `AddedOn`, `BookedState`) 
VALUES ('4570E3D9-CEFA-421E-A084-36E2FD25B4D9', 'B9A3886F-4120-45C8-B060-AC09A4386859', '2020-10-02 21:00:00', 20, '54533C40-D822-4FAE-B066-F2E0CDF04C52', 'Frog & Bucket', 0, '2016-10-01 20:00:00', 0);

INSERT INTO `Spot` (`Id`, `UserId`, `ScheduledFor`, `DurationMinutes`, `TownId`, `VenueName`, `Cancelled`, `AddedOn`, `BookedState`) 
VALUES ('70B48325-53CF-4872-B21D-8D7580072F2D', 'B9A3886F-4120-45C8-B060-AC09A4386859', '2020-10-03 20:10:00', 10, '54533C40-D822-4FAE-B066-F2E0CDF04C52', 'The Comedy Store', 0, '2016-10-01 20:00:00', 1);

INSERT INTO `Spot` (`Id`, `UserId`, `ScheduledFor`, `DurationMinutes`, `TownId`, `VenueName`, `Cancelled`, `AddedOn`, `BookedState`) 
VALUES ('DED0ACB4-08F0-4564-BF9C-B62F665BE24D', 'B9A3886F-4120-45C8-B060-AC09A4386859', '2020-10-03 20:30:00', 10, '54533C40-D822-4FAE-B066-F2E0CDF04C52', 'The Comedy Store', 0, '2016-10-01 20:00:00', 1);

--//@UNDO

DELETE FROM `Spot`;
