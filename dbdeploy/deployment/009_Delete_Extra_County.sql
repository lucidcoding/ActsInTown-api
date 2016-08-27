-- Delete extrenuous Bedfordshire

DELETE FROM [County] WHERE [Id] = (SELECT TOP 1 [Id] FROM [County] WHERE [Name] = 'Bedfordshire');

--//@UNDO
