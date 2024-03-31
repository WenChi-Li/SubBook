create database SunBank;

use SunBank;

-- 建立 User 使用者 表
CREATE TABLE UserData (
    UserId INT IDENTITY(1,1) PRIMARY KEY not null,
    Phonenumber NVARCHAR(255)  NOT NULL,
    Password NVARCHAR(MAX) NOT NULL,
	Salt NVARCHAR(MAX),
    UserName NVARCHAR(255),
    RegistrationTime DATETIME,
    LastLoginTime DATETIME
);

-- 建立 Book 書籍 表
CREATE TABLE Book (
    ISBN int IDENTITY(1,1) PRIMARY KEY  not null,
    Name NVARCHAR(255),
    Author NVARCHAR(255),
    Introduction NVARCHAR(MAX)
);



-- 建立 Inventory 庫存 表
CREATE TABLE Inventory (
    InventoryId INT IDENTITY(1,1) PRIMARY KEY not null,
    ISBN int NOT NULL,
    StoreTime DATETIME,
    Status NVARCHAR(255)
	FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
);

-- 建立 BorrowingRecord 借閱紀錄 表
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
SET status = '在庫'
WHERE InventoryId IN (1, 2, 4);

UPDATE Inventory
SET status = '毀損'
WHERE InventoryId IN (5,6);

select *from UserData;
select *from Inventory
select *from Book;
select *from BorrowingRecord;


--drop table UserData,Book, Inventory,BorrowingRecord;
--drop table BorrowingRecord, UserData;

