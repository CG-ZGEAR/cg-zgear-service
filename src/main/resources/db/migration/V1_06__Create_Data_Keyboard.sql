-- Inserting more Keyboard products
INSERT INTO products (product_name, price, category_id, available)
VALUES
    ('Logitech MX Keys', 99.99, 5, 1),
    ('Razer BlackWidow V3', 139.99, 5, 1),
    ('Corsair K95 RGB Platinum', 199.99, 5, 1);

-- Get newly added product IDs
SET @logitechId = LAST_INSERT_ID();
SET @razerId = @logitechId + 1;
SET @corsairId = @razerId + 1;

-- Inserting product images
INSERT INTO product_images (url, product_id)
VALUES
    ('https://hanoicomputercdn.com/media/product/73707_ban_phim_khong_day_logitech_mx_keys_s_graphite_3.jpg', @logitechId),
    ('https://file.hstatic.net/1000026716/file/gearvn-ban-phim-razer-blackwidow-v3-pro-green-switch_ef6d6c248b7048ed90b18f1cf785d39f.jpg', @razerId),
    ('https://tanphat.com.vn/media/product/3748_38595_corsair_k95_rgb_platinum_xt_mx_blue_ha1.jpg', @corsairId);

-- Inserting product details
INSERT INTO product_details (product_id, description)
VALUES
    (@logitechId, 'Logitech MX Keys with perfect stroke keys for comfortable typing.'),
    (@razerId, 'Razer BlackWidow V3, a mechanical gaming keyboard with Razer Green switches.'),
    (@corsairId, 'Corsair K95 RGB Platinum, a high-end gaming keyboard with Cherry MX Speed switches.');

-- Find specification template IDs
SET @typeTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Type' AND category_id = 5 LIMIT 1);
SET @keySwitchesTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Key Switches' AND category_id = 5 LIMIT 1);
SET @backlightTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Backlight' AND category_id = 5 LIMIT 1);
SET @connectivityTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Connectivity' AND category_id = 5 LIMIT 1);

-- Inserting specifications for new Keyboard products
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (@logitechId, @typeTemplateId, 'Wireless'),
    (@logitechId, @keySwitchesTemplateId, 'Membrane'),
    (@logitechId, @backlightTemplateId, 'Yes'),
    (@logitechId, @connectivityTemplateId, 'Bluetooth/USB'),

    (@razerId, @typeTemplateId, 'Wired'),
    (@razerId, @keySwitchesTemplateId, 'Mechanical'),
    (@razerId, @backlightTemplateId, 'RGB'),
    (@razerId, @connectivityTemplateId, 'USB'),

    (@corsairId, @typeTemplateId, 'Wired'),
    (@corsairId, @keySwitchesTemplateId, 'Mechanical'),
    (@corsairId, @backlightTemplateId, 'RGB'),
    (@corsairId, @connectivityTemplateId, 'USB');
