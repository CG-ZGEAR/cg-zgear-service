INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (1, 'Mari Luckcock', 'mluckcock0', '$2a$04$IqRFSspB.o3glqZ.9ohSSeDLOCOIcaaPxctxLLWusEoWsSFglN0a.',
        'mluckcock0@smugmug.com', 'Female', '03/19/1996', '814-549-8044',
        'https://robohash.org/repudiandaeillofugiat.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (2, 'Whittaker Antonomolii', 'wantonomolii1', '$2a$04$7HU1VtYSmCBOX/CNy8y8jOGA0PvkKj2p.Fxeof4RfbNTQ1SBc/wdy',
        'wantonomolii1@odnoklassniki.ru', 'Male', '06/23/1992', '919-991-6053',
        'https://robohash.org/dolorescorruptiesse.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (3, 'Jonah Klemensiewicz', 'jklemensiewicz2', '$2a$04$CL8g42.LQ0FKocHRUJCSbewqvzKien0t0TBSdob8GvM.kM0MfEn7K',
        'jklemensiewicz2@mac.com', 'Male', '11/23/1997', '687-453-5488',
        'https://robohash.org/sitquiamet.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (4, 'Salomo Langlais', 'slanglais3', '$2a$04$TtbWLrJ9410uul2RgTreiemyQ416kuJNH13bcdBRL2pQPB94tbOZS',
        'slanglais3@webmd.com', 'Male', '07/26/2015', '959-215-8132',
        'https://robohash.org/sittotamblanditiis.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (5, 'Caprice Muzzollo', 'cmuzzollo4', '$2a$04$3MTXo4lFTiSfiZdQfj4lgu6m1toxWB4r41mT6GogaJ3MGusPPiwyW',
        'cmuzzollo4@wisc.edu', 'Female', '08/06/2013', '754-144-9357',
        'https://robohash.org/voluptatequieos.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (6, 'Graham Gridley', 'ggridley5', '$2a$04$d3RgYU58OJcuhZjrw4.wbeRP1gHyEsFbNtrzuVGiqNyPaBIwup1ZG',
        'ggridley5@washington.edu', 'Male', '09/16/1995', '578-355-2554',
        'https://robohash.org/culpaofficiaquasi.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (7, 'Gard Crannell', 'gcrannell6', '$2a$04$qn2UvjhzbXd/WKjyTxNHZOaKoKj/P8qQ7SsELJlrLBNAw.h82K8Fi',
        'gcrannell6@ucoz.com', 'Male', '10/22/2000', '739-701-8626',
        'https://robohash.org/numquamodioodit.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (8, 'Aguste Gypps', 'agypps7', '$2a$04$Xdb6hPeFHXNEVEhnMlmFGuBQJF0PXOl13HHiO2bxLfBH8HbO32jBa',
        'agypps7@jugem.jp', 'Male', '04/29/1992', '796-584-6459',
        'https://robohash.org/consequatursedvel.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (9, 'Holden Broose', 'hbroose8', '$2a$04$pFq5c4WahPvMGF3bD9bIfucd4l/T8cc557hzubsaLsWL72IbW2BeC',
        'hbroose8@tuttocitta.it', 'Male', '09/16/1993', '306-591-2790',
        'https://robohash.org/voluptasenimvoluptatem.png?size=50x50&set=set1');
insert into users (id, full_name, username, password, email, gender, date_of_birth, phone_number, avatar)
values (10, 'Way Evemy', 'wevemy9', '$2a$04$Psk5ulNwo0YM/R58mG3POu6e2g5N8ZwVuRIytj8AJxi3T/2UX8o/y', 'wevemy9@msu.edu',
        'Male', '01/29/1997', '562-402-6951', 'https://robohash.org/numquamofficiisquis.png?size=50x50&set=set1');

INSERT INTO addresses (user_id, street, city, district, ward, is_default)
VALUES (1, '123 Main St', 'Springfield', 'Downtown', 'Ward 1', 1),
       (2, '456 Elm St', 'Shelbyville', 'Uptown', 'Ward 2', 1);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO categories (category_name)
VALUES ('VGA'),
       ('Monitor'),
       ('Laptop'),
       ('Gaming PC'),
       ('Keyboard'),
       ('Mouse'),
       ('Headset'),
       ('Gaming Chair');

INSERT INTO products (product_name, price, category_id, is_deleted)
VALUES ('Nvidia GeForce RTX 3080', 699.99, 1, 0),
       ('Samsung Odyssey G9', 1299.99, 2, 0);


INSERT INTO product_images (url, product_id)
VALUES ('https://www.nvidia.com/content/dam/en-zz/Solutions/geforce/ampere/rtx-3080/images/design/geforce-rtx-3080-4-960.jpg', 1),
       ('https://npcshop.vn/media/product/2607-samsung-odyssey-g9-gaming-49in-cong-1000r-240hz-8.jpeg', 2);

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

-- Specification templates for Laptop
INSERT INTO specification_templates (category_id, spec_key)
VALUES (3, 'CPU'), (3, 'RAM'), (3, 'Storage'), (3, 'Screen Size'), (3, 'Battery Life');

-- Specification templates for Gaming PC
INSERT INTO specification_templates (category_id, spec_key)
VALUES (4, 'CPU'),
       (4, 'GPU'),
       (4, 'RAM'),
       (4, 'Storage'),
       (4, 'Cooling System');

-- Specification templates for Keyboard
INSERT INTO specification_templates (category_id, spec_key)
VALUES (5, 'Type'),
       (5, 'Key Switches'),
       (5, 'Backlight'),
       (5, 'Connectivity');

-- Specification templates for Mouse
INSERT INTO specification_templates (category_id, spec_key)
VALUES (6, 'DPI'),
       (6, 'Sensor Type'),
       (6, 'Buttons'),
       (6, 'Connectivity');

-- Specification templates for Headset
INSERT INTO specification_templates (category_id, spec_key)
VALUES (7, 'Type'),
       (7, 'Connectivity'),
       (7, 'Noise Cancellation'),
       (7, 'Frequency Response');

-- Specification templates for Gaming Chair
INSERT INTO specification_templates (category_id, spec_key)
VALUES (8, 'Material'),
       (8, 'Weight Capacity'),
       (8, 'Adjustable Armrests'),
       (8, 'Recline');

-- For Nvidia GeForce RTX 3080
INSERT INTO product_details (product_id, description)
VALUES (1, 'Can handle all games');
-- For Samsung Odyssey G9
INSERT INTO product_details (product_id, description)
VALUES (2, 'A gaming Monitor for gamers');

-- For VGA
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES (1, 1, '10GB'),
       (1, 2, '285mm'),
       (1, 3, '2');

-- For Monitors
INSERT INTO specifications (product_detail_id, template_id, spec_value)
VALUES (2, 4, '49 inch'),
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

