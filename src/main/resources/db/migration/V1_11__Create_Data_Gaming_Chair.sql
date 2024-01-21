-- Inserting Gaming Chair products
INSERT INTO products (product_name, price, category_id, available)
VALUES
    ('DXRacer Formula Series Gaming Chair', 299.99, 8, 1),
    ('Secretlab Omega Series Gaming Chair', 349.99, 8, 1),
    ('AKRacing Masters Series Pro Gaming Chair', 379.99, 8, 1);

-- Get newly added product IDs
SET @dxracerChairId = LAST_INSERT_ID();
SET @secretlabChairId = @dxracerChairId + 1;
SET @akracingChairId = @secretlabChairId + 1;

-- Inserting product images for Gaming Chair
INSERT INTO product_images (url, product_id)
VALUES
    ('https://product.hstatic.net/200000722513/product/oh-fh08-nw-2_1024x1024_e13ac38e-7715-47ca-9bc2-75b8eb90d3ed_421f210adb3443b9af97e709f81c0e3c_1024x1024.jpg', @dxracerChairId),
    ('https://secretlab.co.uk/cdn/shop/files/turntable_2020_OM_pu_stealth_2-min.jpg?v=2079812668809504478', @secretlabChairId),
    ('https://cdn-amz.woka.io/images/I/619HVmAj1TL.jpg', @akracingChairId);

-- Inserting product details for Gaming Chair
INSERT INTO product_details (product_id, description)
VALUES
    (@dxracerChairId, 'DXRacer Formula Series Gaming Chair with ergonomic design and lumbar support for long gaming sessions.'),
    (@secretlabChairId, 'Secretlab Omega Series Gaming Chair with PRIME™ 2.0 PU leather and customizable armrests.'),
    (@akracingChairId, 'AKRacing Masters Series Pro Gaming Chair with 4D armrests and cold-cured foam padding.');

-- Find specification template IDs for Gaming Chair
SET @materialTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Material' AND category_id = 8 LIMIT 1);
SET @weightCapacityTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Weight Capacity' AND category_id = 8 LIMIT 1);
SET @adjustableArmrestsTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Adjustable Armrests' AND category_id = 8 LIMIT 1);
SET @reclineTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Recline' AND category_id = 8 LIMIT 1);

-- Inserting specifications for Gaming Chair
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (@dxracerChairId, @materialTemplateId, 'PU Leather'),
    (@dxracerChairId, @weightCapacityTemplateId, '220 lbs'),
    (@dxracerChairId, @adjustableArmrestsTemplateId, 'Yes'),
    (@dxracerChairId, @reclineTemplateId, '90 - 135 degrees'),

    (@secretlabChairId, @materialTemplateId, 'PRIME™ 2.0 PU Leather'),
    (@secretlabChairId, @weightCapacityTemplateId, '250 lbs'),
    (@secretlabChairId, @adjustableArmrestsTemplateId, 'Yes'),
    (@secretlabChairId, @reclineTemplateId, '85 - 165 degrees'),

    (@akracingChairId, @materialTemplateId, 'PU Leather'),
    (@akracingChairId, @weightCapacityTemplateId, '330 lbs'),
    (@akracingChairId, @adjustableArmrestsTemplateId, 'Yes'),
    (@akracingChairId, @reclineTemplateId, '90 - 180 degrees');
