-- 禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;

-- 清空表数据
DELETE FROM Piece;
DELETE FROM Item;
DELETE FROM Category;
DELETE FROM Location;

-- 启用外键约束
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO Category (mainCategory, subCategory, catNotes) VALUES
                                                               ('Furniture', 'Table', 'Tables for different purposes'),
                                                               ('Furniture', 'Chair', 'Various types of chairs'),
                                                               ('Storage', 'Shelf', 'Shelves for storage and organization');


INSERT INTO Location (roomNum, shelfNum, shelf, shelfDescription) VALUES
                                                                      (1, 1, 'A1', 'Room 1, Shelf A1 near entrance'),
                                                                      (1, 2, 'A2', 'Room 1, Shelf A2 near window'),
                                                                      (2, 1, 'B1', 'Room 2, Shelf B1 next to wall'),
                                                                      (2, 2, 'B2', 'Room 2, Shelf B2 center aisle');

INSERT INTO Item (ItemID, iDescription, photo, color, isNew, hasPieces, material, mainCategory, subCategory) VALUES
                                                                                                                 (1, 'Wooden Table', NULL, 'Brown', TRUE, TRUE, 'Wood', 'Furniture', 'Table'),
                                                                                                                 (2, 'Plastic Chair', NULL, 'White', TRUE, TRUE, 'Plastic', 'Furniture', 'Chair'),
                                                                                                                 (3, 'Metal Shelf', NULL, 'Gray', TRUE, TRUE, 'Metal', 'Storage', 'Shelf');

INSERT INTO Piece (ItemID, pieceNum, pDescription, length, width, height, roomNum, shelfNum, pNotes) VALUES
-- Wooden Table pieces
(1, 1, 'Tabletop', 120, 60, 5, 1, 1, 'Main tabletop'),
(1, 2, 'Leg 1', 5, 5, 60, 1, 1, 'First leg of the table'),
(1, 3, 'Leg 2', 5, 5, 60, 1, 2, 'Second leg of the table'),

-- Plastic Chair pieces
(2, 1, 'Seat', 50, 50, 5, 2, 1, 'Main seat part'),
(2, 2, 'Backrest', 50, 5, 40, 2, 1, 'Backrest of the chair'),
(2, 3, 'Legs', 5, 5, 45, 2, 2, 'Legs of the chair'),

-- Metal Shelf pieces
(3, 1, 'Shelf 1', 100, 40, 2, 2, 1, 'First shelf'),
(3, 2, 'Shelf 2', 100, 40, 2, 2, 2, 'Second shelf'),
(3, 3, 'Frame', 100, 40, 200, 2, 1, 'Main frame of the shelf');



