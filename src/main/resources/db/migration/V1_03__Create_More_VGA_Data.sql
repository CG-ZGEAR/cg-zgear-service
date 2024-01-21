-- Inserting more VGA products
INSERT INTO products (product_name, price, category_id, available)
VALUES
    ('AMD Radeon RX 6800 XT', 649.99, 1, 1),
    ('Asus ROG Strix RTX 2070', 499.99, 1, 1),
    ('Gigabyte GeForce GTX 1660 Ti', 279.99, 1, 1);

-- Get newly added product IDs
SET @rx6800xtId = LAST_INSERT_ID();
SET @rtx2070Id = @rx6800xtId + 1;
SET @gtx1660TiId = @rtx2070Id + 1;

-- Inserting product images
INSERT INTO product_images (url, product_id)
VALUES
    ('https://amdvietnam.vn/data/upload/media/files/amd-radeon-rx-6800-xt/2022-05-13_15-47-05.png', @rx6800xtId),
    ('https://product.hstatic.net/1000189326/product/2070s_ad_28392c128b6d430fad7cbfc8178d22a1_grande.jpg', @rtx2070Id),
    ('https://content2.rozetka.com.ua/goods/images/big_tile/127065800.png', @gtx1660TiId);

-- Inserting product details
INSERT INTO product_details (product_id, description)
VALUES
    (@rx6800xtId, 'High-end gaming performance with the latest AMD RDNA 2 architecture.'),
    (@rtx2070Id, 'ROG Strix graphics card delivers top-tier gaming performance.'),
    (@gtx1660TiId, 'Great value and performance for mainstream gaming.');

-- Find specification template IDs
SET @brandTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'brand' AND category_id = 1 LIMIT 1);
SET @vramTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'VRAM' AND category_id = 1 LIMIT 1);
SET @sizeTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Size' AND category_id = 1 LIMIT 1);
SET @fansTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Fans' AND category_id = 1 LIMIT 1);
SET @clockSpeedTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Clock Speed' AND category_id = 1 LIMIT 1);
SET @connectionTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'PC connection' AND category_id = 1 LIMIT 1);

-- Inserting specifications for new VGA products
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (@rx6800xtId, @brandTemplateId, 'AMD'),
    (@rx6800xtId, @vramTemplateId, '16GB'),
    (@rx6800xtId, @sizeTemplateId, '267mm'),
    (@rx6800xtId, @fansTemplateId, '3'),
    (@rx6800xtId, @clockSpeedTemplateId, '2105 MHz'),
    (@rx6800xtId, @connectionTemplateId, 'HDMI/DisplayPort'),

    (@rtx2070Id, @brandTemplateId, 'Asus'),
    (@rtx2070Id, @vramTemplateId, '8GB'),
    (@rtx2070Id, @sizeTemplateId, '299mm'),
    (@rtx2070Id, @fansTemplateId, '3'),
    (@rtx2070Id, @clockSpeedTemplateId, '1620 MHz'),
    (@rtx2070Id, @connectionTemplateId, 'HDMI/DisplayPort'),

    (@gtx1660TiId, @brandTemplateId, 'Gigabyte'),
    (@gtx1660TiId, @vramTemplateId, '6GB'),
    (@gtx1660TiId, @sizeTemplateId, '225mm'),
    (@gtx1660TiId, @fansTemplateId, '2'),
    (@gtx1660TiId, @clockSpeedTemplateId, '1800 MHz'),
    (@gtx1660TiId, @connectionTemplateId, 'HDMI/DisplayPort');
