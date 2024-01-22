-- Inserting more Laptop products
INSERT INTO products (product_name, price, category_id, available)
VALUES
    ('Apple MacBook Pro', 1999.99, 3, 1),
    ('Dell XPS 15', 1499.99, 3, 1),
    ('HP Spectre x360', 1349.99, 3, 1);

-- Get newly added product IDs
SET @macBookProId = LAST_INSERT_ID();
SET @dellXPSId = @macBookProId + 1;
SET @hpSpectreId = @dellXPSId + 1;

-- Inserting product images
INSERT INTO product_images (url, product_id)
VALUES
    ('https://product.hstatic.net/1000283534/product/mauxam_1_82acd60875cc41b2af165508ca5fbc0b.jpeg', @macBookProId),
    ('https://bizweb.dktcdn.net/100/408/235/products/dell-xps-9530-2023-15-inch-laptopvang-2-scaled.jpg?v=1690796182413', @dellXPSId),
    ('https://gamalaptop.vn/wp-content/uploads/2021/07/HP-Spectre-x360-15-01.jpg', @hpSpectreId);

-- Inserting product details
INSERT INTO product_details (product_id, description)
VALUES
    (@macBookProId, 'High-performance MacBook Pro with Apple M1 chip.'),
    (@dellXPSId, 'Dell XPS 15 with stunning display and high build quality.'),
    (@hpSpectreId, 'HP Spectre x360, a versatile 2-in-1 laptop with great features.');

-- Find specification template IDs
SET @cpuTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'CPU' AND category_id = 3 LIMIT 1);
SET @ramTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'RAM' AND category_id = 3 LIMIT 1);
SET @storageTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Storage' AND category_id = 3 LIMIT 1);
SET @screenSizeTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Screen Size' AND category_id = 3 LIMIT 1);
SET @batteryLifeTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Battery Life' AND category_id = 3 LIMIT 1);

-- Inserting specifications for new Laptop products
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (@macBookProId, @cpuTemplateId, 'Apple M1'),
    (@macBookProId, @ramTemplateId, '16GB'),
    (@macBookProId, @storageTemplateId, '1TB SSD'),
    (@macBookProId, @screenSizeTemplateId, '13 inch'),
    (@macBookProId, @batteryLifeTemplateId, '20 hours'),

    (@dellXPSId, @cpuTemplateId, 'Intel Core i7'),
    (@dellXPSId, @ramTemplateId, '16GB'),
    (@dellXPSId, @storageTemplateId, '512GB SSD'),
    (@dellXPSId, @screenSizeTemplateId, '15 inch'),
    (@dellXPSId, @batteryLifeTemplateId, '10 hours'),

    (@hpSpectreId, @cpuTemplateId, 'Intel Core i5'),
    (@hpSpectreId, @ramTemplateId, '8GB'),
    (@hpSpectreId, @storageTemplateId, '256GB SSD'),
    (@hpSpectreId, @screenSizeTemplateId, '13.3 inch'),
    (@hpSpectreId, @batteryLifeTemplateId, '12 hours');
