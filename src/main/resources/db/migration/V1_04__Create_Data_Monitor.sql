-- Inserting more Monitor products
INSERT INTO products (product_name, price, category_id, available)
VALUES
    ('LG UltraGear 27GL850', 449.99, 2, 1),
    ('Acer Predator XB273K', 829.99, 2, 1),
    ('Dell S3220DGF', 399.99, 2, 1);

-- Get newly added product IDs
SET @lgUltraGearId = LAST_INSERT_ID();
SET @acerPredatorId = @lgUltraGearId + 1;
SET @dellS3220DGFId = @acerPredatorId + 1;

-- Inserting product images
INSERT INTO product_images (url, product_id)
VALUES
    ('https://product.hstatic.net/200000309925/product/d-01_8f13fde68ada415e87ff91bfc124e54f_595a7789e53f47e9bc02fff479377cbb_master.jpg', @lgUltraGearId),
    ('https://cdn-amz.woka.io/images/I/71KaGlmHBSL.jpg', @acerPredatorId),
    ('https://anphat.com.vn/media/product/36164_d18.jpg', @dellS3220DGFId);

-- Inserting product details
INSERT INTO product_details (product_id, description)
VALUES
    (@lgUltraGearId, '27 inch gaming monitor with exceptional color accuracy and response time.'),
    (@acerPredatorId, '4K resolution gaming monitor with G-Sync technology.'),
    (@dellS3220DGFId, '32 inch curved gaming monitor with FreeSync for smooth gameplay.');

-- Find specification template IDs
SET @screenSizeTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Screen Size' AND category_id = 2 LIMIT 1);
SET @refreshRateTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Refresh Rate' AND category_id = 2 LIMIT 1);
SET @panelTypeTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Panel Type' AND category_id = 2 LIMIT 1);

-- Inserting specifications for new Monitor products
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (@lgUltraGearId, @screenSizeTemplateId, '27 inch'),
    (@lgUltraGearId, @refreshRateTemplateId, '144Hz'),
    (@lgUltraGearId, @panelTypeTemplateId, 'IPS'),

    (@acerPredatorId, @screenSizeTemplateId, '27 inch'),
    (@acerPredatorId, @refreshRateTemplateId, '144Hz'),
    (@acerPredatorId, @panelTypeTemplateId, 'IPS'),

    (@dellS3220DGFId, @screenSizeTemplateId, '32 inch'),
    (@dellS3220DGFId, @refreshRateTemplateId, '165Hz'),
    (@dellS3220DGFId, @panelTypeTemplateId, 'VA');
