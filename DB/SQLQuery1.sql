create database SunBank;

use SunBank;

-- �إ� User �ϥΪ� ��
CREATE TABLE UserData (
    UserId INT IDENTITY(1,1) PRIMARY KEY not null,
    Phonenumber NVARCHAR(255)  NOT NULL,
    Password NVARCHAR(MAX) NOT NULL,
	Salt NVARCHAR(MAX),
    UserName NVARCHAR(255),
    RegistrationTime DATETIME,
    LastLoginTime DATETIME
);

-- �إ� Book ���y ��
CREATE TABLE Book (
    ISBN int IDENTITY(1,1) PRIMARY KEY  not null,
    Name NVARCHAR(255),
    Author NVARCHAR(255),
    Introduction NVARCHAR(MAX)
);



-- �إ� Inventory �w�s ��
CREATE TABLE Inventory (
    InventoryId INT IDENTITY(1,1) PRIMARY KEY not null,
    ISBN int NOT NULL,
    StoreTime DATETIME,
    Status NVARCHAR(255)
	FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
);

-- �إ� BorrowingRecord �ɾ\���� ��
CREATE TABLE BorrowingRecord (
    BorrowingRecordId INT IDENTITY(1,1) PRIMARY KEY not null,
    UserId INT,
    InventoryId INT,
    BorrowingTime DATETIME,
    ReturnTime DATETIME,
    FOREIGN KEY (UserId) REFERENCES UserData(UserId),
    FOREIGN KEY (InventoryId) REFERENCES Inventory(InventoryId)
);


UPDATE Inventory
SET status = '�b�w'
WHERE InventoryId IN (1, 2, 4);

UPDATE Inventory
SET status = '���l'
WHERE InventoryId IN (5,6);

select *from UserData;
select *from Inventory
select *from Book;
select *from BorrowingRecord;


--drop table UserData,Book, Inventory,BorrowingRecord;
--drop table BorrowingRecord, UserData;

