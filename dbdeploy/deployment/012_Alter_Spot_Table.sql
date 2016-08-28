-- Create Spot

EXEC sp_rename 'Spot.VanueName', 'VenueName', 'COLUMN'; 

ALTER TABLE [Spot] ADD [Cancelled] BIT NOT NULL DEFAULT(0)

--//@UNDO

ALTER TABLE [Spot] DROP COLUMN [Cancelled];

EXEC sp_rename 'Spot.VenueName', 'VanueName', 'COLUMN'; 
