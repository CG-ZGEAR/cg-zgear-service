-- Inserting Gaming PC products
INSERT INTO products (product_name, price, category_id, available)
VALUES
    ('Custom Gaming PC Intel Core i7, RTX 3080, 16GB RAM, 1TB SSD', 2299.99, 4, 1),
    ('Gaming PC AMD Ryzen 9, RX 6800 XT, 32GB RAM, 2TB HDD + 1TB SSD', 2799.99, 4, 1),
    ('Alienware Aurora R10 Gaming Desktop Ryzen 7, RTX 3070, 16GB RAM, 512GB SSD', 1899.99, 4, 1);

-- Get newly added product IDs
SET @customPCId = LAST_INSERT_ID();
SET @gamingPCId1 = @customPCId + 1;
SET @gamingPCId2 = @gamingPCId1 + 1;

-- Inserting product images for Gaming PC
INSERT INTO product_images (url, product_id)
VALUES
    ('https://vrlatech.com/wp-content/uploads/2021/12/Phoenix-4080-Main.jpg.webp', @customPCId),
    ('https://file.hstatic.net/200000536009/collection/pc_gaming_core_i7_a89473a0ce934d868844387097dca141.jpg', @gamingPCId1),
    ('https://nguyencongpc.vn/media/product/25527-black-friday-gaming-06.jpg', @gamingPCId2);

-- Inserting product details for Gaming PC
INSERT INTO product_details (product_id, description)
VALUES
    (@customPCId, 'High-performance custom gaming PC with Intel Core i7, NVIDIA RTX 3080, 16GB RAM, and 1TB SSD.'),
    (@gamingPCId1, 'Powerful gaming PC featuring AMD Ryzen 9, AMD RX 6800 XT, 32GB RAM, and 2TB HDD + 1TB SSD.'),
    (@gamingPCId2, 'Alienware Aurora R10 Gaming Desktop with Ryzen 7, NVIDIA RTX 3070, 16GB RAM, and 512GB SSD.');

-- Find specification template IDs for Gaming PC
SET @cpuTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'CPU' AND category_id = 4 LIMIT 1);
SET @gpuTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'GPU' AND category_id = 4 LIMIT 1);
SET @ramTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'RAM' AND category_id = 4 LIMIT 1);
SET @storageTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Storage' AND category_id = 4 LIMIT 1);
SET @coolingTemplateId = (SELECT id FROM specification_templates WHERE spec_key = 'Cooling System' AND category_id = 4 LIMIT 1);

-- Inserting specifications for Gaming PC
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (@customPCId, @cpuTemplateId, 'Intel Core i7'),
    (@customPCId, @gpuTemplateId, 'NVIDIA RTX 3080'),
    (@customPCId, @ramTemplateId, '16GB'),
    (@customPCId, @storageTemplateId, '1TB SSD'),
    (@customPCId, @coolingTemplateId, 'Liquid Cooling'),

    (@gamingPCId1, @cpuTemplateId, 'AMD Ryzen 9'),
    (@gamingPCId1, @gpuTemplateId, 'AMD RX 6800 XT'),
    (@gamingPCId1, @ramTemplateId, '32GB'),
    (@gamingPCId1, @storageTemplateId, '2TB HDD + 1TB SSD'),
    (@gamingPCId1, @coolingTemplateId, 'Air Cooling'),

    (@gamingPCId2, @cpuTemplateId, 'AMD Ryzen 7'),
    (@gamingPCId2, @gpuTemplateId, 'NVIDIA RTX 3070'),
    (@gamingPCId2, @ramTemplateId, '16GB'),
    (@gamingPCId2, @storageTemplateId, '512GB SSD'),
    (@gamingPCId2, @coolingTemplateId, 'Air Cooling');
