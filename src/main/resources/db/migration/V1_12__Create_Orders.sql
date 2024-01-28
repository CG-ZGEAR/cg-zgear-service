-- Insert data into orders and order_items for new products
INSERT INTO orders (status, date_created, total,address_id)
VALUES
    ('PAID', NOW(), 649.99,1),
    ('SHIPPED', NOW(), 829.99,1),
    ('SHIPPED', NOW(), 279.99,1),
    ('SHIPPED', NOW(), 449.99,1),
    ('SHIPPED', NOW(), 399.99,1);

-- Order 3 items
INSERT INTO order_items (order_id, product_id, quantity, sub_total)
VALUES
    (3, 3, 1, 649.99);

-- Order 4 items
INSERT INTO order_items (order_id, product_id, quantity, sub_total)
VALUES
    (4, 4, 2, 1659.98);

-- Order 5 items
INSERT INTO order_items (order_id, product_id, quantity, sub_total)
VALUES
    (5, 5, 1, 279.99);

-- Order 6 items
INSERT INTO order_items (order_id, product_id, quantity, sub_total)
VALUES
    (6, 6, 1, 449.99);

-- Order 7 items
INSERT INTO order_items (order_id, product_id, quantity, sub_total)
VALUES
    (7, 7, 1, 399.99);
