-- Inserting Mouse products
INSERT INTO products (product_name, price, category_id, available)
VALUES
    ('Logitech G Pro X Superlight Wireless Gaming Mouse', 149.99, 6, 1),
    ('Razer DeathAdder Elite Gaming Mouse', 69.99, 6, 1),
    ('SteelSeries Rival 600 Gaming Mouse', 79.99, 6, 1);

-- Get newly added product IDs
SET @logitechMouseId = LAST_INSERT_ID();
SET @razerMouseId = @logitechMouseId + 1;
SET @steelSeriesMouseId = @razerMouseId + 1;

-- Inserting product images for Mouse
INSERT INTO product_images (url, product_id)
VALUES
    ('https://product.hstatic.net/200000722513/product/g-pro-x-superlight-wireless-black-666_83650815ce2e486f9108dbbb17c29159_1450bb4a9bd34dcb92fc77f627eb600d_1024x1024.jpg', @logitechMouseId),
    ('https://nguyenvu-store-medias.tn-cdn.net/2020/03/ba898.jpg', @razerMouseId),
    ('https://phucanhcdn.com/media/product/39633_steelseries_rival_600__rgb___62446.gif', @steelSeriesMouseId);

-- Inserting product details for Mouse
INSERT INTO product_details (product_id, description)
VALUES
    (@logitechMouseId, 'Logitech G Pro X Superlight Wireless Gaming Mouse with HERO sensor for precision and speed.'),
    (@razerMouseId, 'Razer DeathAdder Elite Gaming Mouse with advanced optical sensor and ergonomic design.'),
    (@steelSeriesMouseId, 'SteelSeries Rival 600 Gaming Mouse with dual sensor system and customizable weight.');

-- Find specification template IDs for Mouse
SET @dpiTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'DPI' AND category_id = 6 LIMIT 1);
SET @sensorTypeTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Sensor Type' AND category_id = 6 LIMIT 1);
SET @buttonsTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Buttons' AND category_id = 6 LIMIT 1);
SET @connectivityTemplateIdMouse = (SELECT id FROM specification_templates WHERE spec_key = 'Connectivity' AND category_id = 6 LIMIT 1);

-- Inserting specifications for Mouse
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (@logitechMouseId, @dpiTemplateId, '25,600 DPI'),
    (@logitechMouseId, @sensorTypeTemplateId, 'Optical'),
    (@logitechMouseId, @buttonsTemplateId, '6 programmable buttons'),
    (@logitechMouseId, @connectivityTemplateIdMouse, 'Wireless'),

    (@razerMouseId, @dpiTemplateId, '16,000 DPI'),
    (@razerMouseId, @sensorTypeTemplateId, 'Optical'),
    (@razerMouseId, @buttonsTemplateId, '7 programmable buttons'),
    (@razerMouseId, @connectivityTemplateIdMouse, 'Wired'),

    (@steelSeriesMouseId, @dpiTemplateId, '12,000 DPI'),
    (@steelSeriesMouseId, @sensorTypeTemplateId, 'Dual Sensor System'),
    (@steelSeriesMouseId, @buttonsTemplateId, '7 programmable buttons'),
    (@steelSeriesMouseId, @connectivityTemplateIdMouse, 'Wired');
