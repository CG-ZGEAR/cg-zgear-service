-- Inserting Headset products
INSERT INTO products (product_name, price, category_id, available)
VALUES
    ('HyperX Cloud II Gaming Headset', 99.99, 7, 1),
    ('Logitech G Pro X Gaming Headset', 129.99, 7, 1),
    ('SteelSeries Arctis 7 Wireless Gaming Headset', 149.99, 7, 1);

-- Get newly added product IDs
SET @hyperXHeadsetId = LAST_INSERT_ID();
SET @logitechHeadsetId = @hyperXHeadsetId + 1;
SET @steelSeriesHeadsetId = @logitechHeadsetId + 1;

-- Inserting product images for Headset
INSERT INTO product_images (url, product_id)
VALUES
    ('https://row.hyperx.com/cdn/shop/files/hyperx_cloud_ii_red_2_main_mixer_2048x2048.jpg?v=1699574292', @hyperXHeadsetId),
    ('https://product.hstatic.net/200000722513/product/gvn_logitech_prox_79c556630c454086baf1bee06c577ab7_3471d9d886fd4dbe8ab5ae6bed9f4d78_1024x1024.png', @logitechHeadsetId),
    ('https://nguyenvu-store-medias.tn-cdn.net/2020/03/Tai-nghe-SteelSeries-Arctis-7-2019-Edition-%E2%80%93-Black-1.jpg', @steelSeriesHeadsetId);

-- Inserting product details for Headset
INSERT INTO product_details (product_id, description)
VALUES
    (@hyperXHeadsetId, 'HyperX Cloud II Gaming Headset with 7.1 virtual surround sound and detachable noise-canceling microphone.'),
    (@logitechHeadsetId, 'Logitech G Pro X Gaming Headset with Blue VO!CE microphone technology and customizable sound profiles.'),
    (@steelSeriesHeadsetId, 'SteelSeries Arctis 7 Wireless Gaming Headset with lag-free wireless audio and ClearCast noise-canceling microphone.');

-- Find specification template IDs for Headset
SET @typeTemplateIdHeadset = (SELECT id FROM specification_templates WHERE spec_key = 'Type' AND category_id = 7 LIMIT 1);
SET @connectivityTemplateIdHeadset = (SELECT id FROM specification_templates WHERE spec_key = 'Connectivity' AND category_id = 7 LIMIT 1);
SET @noiseCancellationTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Noise Cancellation' AND category_id = 7 LIMIT 1);
SET @frequencyResponseTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Frequency Response' AND category_id = 7 LIMIT 1);

-- Inserting specifications for Headset
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (@hyperXHeadsetId, @typeTemplateIdHeadset, 'Wired'),
    (@hyperXHeadsetId, @connectivityTemplateIdHeadset, 'USB'),
    (@hyperXHeadsetId, @noiseCancellationTemplateId, 'Yes'),
    (@hyperXHeadsetId, @frequencyResponseTemplateId, '15Hz - 25kHz'),

    (@logitechHeadsetId, @typeTemplateIdHeadset, 'Wired'),
    (@logitechHeadsetId, @connectivityTemplateIdHeadset, 'USB/3.5mm Jack'),
    (@logitechHeadsetId, @noiseCancellationTemplateId, 'Yes'),
    (@logitechHeadsetId, @frequencyResponseTemplateId, '20Hz - 20kHz'),

    (@steelSeriesHeadsetId, @typeTemplateIdHeadset, 'Wireless'),
    (@steelSeriesHeadsetId, @connectivityTemplateIdHeadset, '2.4GHz Wireless'),
    (@steelSeriesHeadsetId, @noiseCancellationTemplateId, 'Yes'),
    (@steelSeriesHeadsetId, @frequencyResponseTemplateId, '20Hz - 20kHz');
