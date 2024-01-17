-- Insert Coupons
INSERT INTO coupons (code, discount_type, discount_amount, max_uses, expire_date, active)
VALUES ('20OFF', 'PERCENT', 20.00, 5, LAST_DAY(NOW()), TRUE),
       ('50CASH', 'FIXED_AMOUNT', 50.00, 10, NULL, TRUE);

-- Insert Product Discounts
INSERT INTO product_discounts (product_id, discount_type, discount_amount, start_date, end_date, active)
VALUES ((SELECT id FROM products WHERE product_name = 'Nvidia GeForce RTX 3080'), 'PERCENT', 15.00, NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY), TRUE),
       ((SELECT id FROM products WHERE product_name = 'Samsung Odyssey G9'), 'FIXED_AMOUNT', 100.00, NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), TRUE);

-- Insert Discount Coupons associations
INSERT INTO discount_coupons (discount_id, coupon_id)
VALUES ((SELECT id FROM product_discounts WHERE product_id = (SELECT id FROM products WHERE product_name = 'Nvidia GeForce RTX 3080')),
        (SELECT id FROM coupons WHERE code = '20OFF')),
       ((SELECT id FROM product_discounts WHERE product_id = (SELECT id FROM products WHERE product_name = 'Samsung Odyssey G9')),
        (SELECT id FROM coupons WHERE code = '50CASH'));
