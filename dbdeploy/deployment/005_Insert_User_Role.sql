-- Populate User, Permission, RolePermission and User tables.

INSERT INTO [Role] (Id, Name, Description) VALUES ('8BB5C141-1F05-480E-9B7F-8070269BFD46', 'Admin', 'Admin User');

INSERT INTO [Role] (Id, Name, Description) VALUES ('2C6E33B8-BD7C-492C-807D-B4B1BCAE5F4F', 'Customer', 'Admin User');

INSERT INTO [Permission] (Id, Name, Description) VALUES ('326FBC6E-65EC-48ED-B381-7F3648D404FB', 'MakeBooking', 'Make Booking');
INSERT INTO [Permission] (Id, Name, Description) VALUES ('6BE24D48-453A-4801-9535-8498A3391B74', 'ViewAllBookings', 'View All Bookings');

INSERT INTO [RolePermission] (Id, PermissionId, RoleId) VALUES ('5B5C757D-8383-45AF-AE89-D30C711144A6', '326FBC6E-65EC-48ED-B381-7F3648D404FB', '8BB5C141-1F05-480E-9B7F-8070269BFD46');
INSERT INTO [RolePermission] (Id, PermissionId, RoleId) VALUES ('5C958082-B286-46DC-BBB6-3C9A2B970D13', '6BE24D48-453A-4801-9535-8498A3391B74', '8BB5C141-1F05-480E-9B7F-8070269BFD46');
INSERT INTO [RolePermission] (Id, PermissionId, RoleId) VALUES ('5105CF79-2E2D-4E84-93FD-595BCFB3FAFC', '326FBC6E-65EC-48ED-B381-7F3648D404FB', '2C6E33B8-BD7C-492C-807D-B4B1BCAE5F4F');

INSERT INTO [User] (Id, Username, Password, PasswordSalt, FirstName, LastName, Email, Enabled, RoleId) 
VALUES ('B9A3886F-4120-45C8-B060-AC09A4386859', 'barry@blue.com', 'JDJhJDEwJC5ERWNTRER6R21VV21pWWNRZW5aSHVFSi5JMWljT1BmRU9CSGdiU2xYNi55c0lIQkRpODd1', '', 'Barry', 'Blue', 'barry@blue.com', 1, '8BB5C141-1F05-480E-9B7F-8070269BFD46');

INSERT INTO [User] (Id, Username, Password, PasswordSalt, FirstName, LastName, Email, Enabled, RoleId)
VALUES ('8508E688-4059-4208-9CB6-B23DC436F501', 'veronica@violet.com', 'JDJhJDEwJC5ERWNTRER6R21VV21pWWNRZW5aSHVFSi5JMWljT1BmRU9CSGdiU2xYNi55c0lIQkRpODd1', '$2a$10$.DEcSDDzGmUWmiYcQenZHu', 'Veronica', 'Violet', 'veronica@violet.com', 1, '2C6E33B8-BD7C-492C-807D-B4B1BCAE5F4F');

--//@UNDO

DELETE FROM [User];
DELETE FROM [RolePermission];
DELETE FROM [Permission];
DELETE FROM [Role];
