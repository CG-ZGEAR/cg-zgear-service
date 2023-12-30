INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO users (date_of_birth, email, full_name, username, password, phone_number, avatar, is_deleted)
VALUES ('1990-01-01', 'john.doe@example.com', 'John Doe', 'john_doe', 'password123', '1234567890', 'avatar1.jpg', 0),
       ('1995-02-02', 'jane.doe@example.com', 'Jane Doe', 'jane_doe', 'password123', '0987654321', 'avatar2.jpg', 0);

INSERT INTO addresses (user_id, street, city, district, ward, is_default)
VALUES (1, '123 Main St', 'Springfield', 'Downtown', 'Ward 1', 1),
       (2, '456 Elm St', 'Shelbyville', 'Uptown', 'Ward 2', 1);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO categories (category_name)
VALUES ('VGA'),
       ('Monitor');

INSERT INTO products (product_name, price, category_id, is_deleted)
VALUES ('Nvidia GeForce RTX 3080', 699.99, 1, 0),
       ('Samsung Odyssey G9', 1299.99, 2, 0);


INSERT INTO product_images (url, product_id)
VALUES ('image1.jpg', 1),
       ('image2.jpg', 2);

-- For VGA
INSERT INTO specification_templates (category_id, spec_key)
VALUES (1, 'VRAM'),
       (1, 'Size'),
       (1, 'Fans');

-- For Monitors
INSERT INTO specification_templates (category_id, spec_key)
VALUES (2, 'Screen Size'),
       (2, 'Refresh Rate'),
       (2, 'Panel Type');

-- For Nvidia GeForce RTX 3080
INSERT INTO product_details (product_id, description)
VALUES (1, 'Can handle all games');
-- For Samsung Odyssey G9
INSERT INTO product_details (product_id, description)
VALUES (2, 'A gaming Monitor for gamers');

-- For VGA
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (1, 1, '10GB'),
    (1, 2, '285mm'),
    (1, 3, '2');

-- For Monitors
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES
    (2, 4, '49 inch'),
    (2, 5, '240Hz'),
    (2, 6, 'QLED');

INSERT INTO carts (user_id, cart_status, is_deleted)
VALUES (1, 'Active', 0),
       (2, 'Active', 0);

INSERT INTO cartlines (cart_id, product_id, quantity)
VALUES (1, 1, 1),
       (2, 2, 1);

INSERT INTO orders (status, cart_id, date_created, total)
VALUES ('Processed', 1, NOW(), 699.99),
       ('Shipped', 2, NOW(), 1299.99);

