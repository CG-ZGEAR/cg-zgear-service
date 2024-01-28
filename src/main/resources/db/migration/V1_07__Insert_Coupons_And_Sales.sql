
INSERT INTO coupons (code, discount_type, discount_amount, max_uses, expire_date, active)
VALUES
    ('WELCOME10', 'PERCENT', 10.00, 100, '2024-12-31', TRUE),
    ('SUMMER2024', 'PERCENT', 15.00, 200, '2024-08-31', TRUE),
    ('50OFF', 'FIXED_AMOUNT', 50.00, 50, '2024-12-31', TRUE),
    ('NEWYEAR2024', 'PERCENT', 20.00, 500, '2024-01-31', TRUE),
    ('100DISCOUNT', 'FIXED_AMOUNT', 100.00, 150, '2024-12-31', TRUE);

INSERT INTO product_discounts (product_id, discount_type, discount_amount, start_date, end_date, active)
VALUES
    (1, 'PERCENT', 5.00, '2024-01-01', '2024-01-31', TRUE),
    (2, 'FIXED_AMOUNT', 20.00, '2024-01-01', '2024-02-28', TRUE),
    (3, 'PERCENT', 10.00, '2024-01-01', '2024-03-31', TRUE),
    (4, 'FIXED_AMOUNT', 30.00, '2024-01-01', '2024-04-30', TRUE),
    (5, 'PERCENT', 15.00, '2024-01-01', '2024-05-31', TRUE);
