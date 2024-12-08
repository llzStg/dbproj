INSERT INTO Ordered (orderID, orderDate, orderNotes, supervisor, client) VALUES
    (1, '2024-12-01', 'Order for client A', 'supervisor1', 'clientA');

INSERT INTO ItemIn (ItemID, orderID, found) VALUES
    (1, 1, TRUE),
    (2, 1, TRUE);
