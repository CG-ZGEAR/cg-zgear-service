CREATE TABLE IF NOT EXISTS roles (
                                     id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                     date_of_birth VARCHAR(20),
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    avatar VARCHAR(255),
    gender VARCHAR(10),
    activated BIT DEFAULT 0,
    is_deleted BIT DEFAULT 0,
    otp_code VARCHAR(10),
    otpExpiration DATETIME,
    CONSTRAINT users_uk UNIQUE (username, email, phone_number)
    );

CREATE TABLE IF NOT EXISTS addresses (
                                         id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                         user_id BIGINT,
                                         street VARCHAR(45),
    city VARCHAR(45),
    district VARCHAR(45),
    ward VARCHAR(45),
    is_deleted BIT DEFAULT 0,
    is_default BIT DEFAULT 0,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS users_roles (
                                           user_id BIGINT NOT NULL,
                                           role_id BIGINT NOT NULL,
                                           CONSTRAINT users_roles_pk PRIMARY KEY (user_id, role_id),
    CONSTRAINT users_roles_roles_fk FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT users_roles_users_fk FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                          category_name VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS products (
                                        id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                        product_name VARCHAR(255),
    price DOUBLE(10,2),
    category_id BIGINT NOT NULL,
    available BIT DEFAULT 1,
    CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES categories (id)
    );

CREATE TABLE IF NOT EXISTS product_images (
                                              id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                              url VARCHAR(255),
    product_id BIGINT NOT NULL,
    CONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES products (id)
    );

CREATE TABLE IF NOT EXISTS specification_templates (
                                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                       category_id BIGINT NOT NULL,
                                                       spec_key VARCHAR(255) NOT NULL,
    UNIQUE KEY (category_id, spec_key),
    FOREIGN KEY (category_id) REFERENCES categories (id)
    );

CREATE TABLE IF NOT EXISTS product_details (
                                               id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                               product_id BIGINT NOT NULL UNIQUE,
                                               description TEXT,
    CONSTRAINT products_id_fk FOREIGN KEY (product_id) REFERENCES products (id)
    );

CREATE TABLE IF NOT EXISTS specifications (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              product_detail_id BIGINT NOT NULL,
                                              template_id BIGINT NOT NULL,
                                              spec_value VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_detail_id) REFERENCES product_details (id),
    FOREIGN KEY (template_id) REFERENCES specification_templates (id)
    );

CREATE TABLE IF NOT EXISTS carts (
                                     id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                     user_id BIGINT NULL,
                                    session_id VARCHAR(255) NULL,
                FOREIGN KEY (user_id) REFERENCES users (id),
    total DOUBLE
    );

CREATE TABLE IF NOT EXISTS cart_items (
                                         id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                         cart_id BIGINT NOT NULL,
                                         product_id BIGINT NOT NULL,
                                         quantity INT,
                                         sub_total DOUBLE,
                                         FOREIGN KEY (cart_id) REFERENCES carts (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
    );

CREATE TABLE IF NOT EXISTS orders (
                                      id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                      status ENUM('PENDING', 'PAID', 'SHIPPED'),
    date_created DATETIME,
    total DOUBLE,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES addresses(id),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS order_items (
                                          id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                          order_id BIGINT NOT NULL,
                                          product_id BIGINT NOT NULL,
                                          quantity INT,
                                          sub_total DOUBLE,
                                          FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
    );

-- Coupons
CREATE TABLE coupons (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         code VARCHAR(50) UNIQUE,
                         discount_type ENUM('PERCENT', 'FIXED_AMOUNT'),
                         discount_amount DOUBLE(10,2),
                         max_uses INT,
                         expire_date DATE,
                         active BOOLEAN DEFAULT TRUE
);

-- Product discounts
CREATE TABLE product_discounts (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   product_id BIGINT,
                                   discount_type ENUM('PERCENT', 'FIXED_AMOUNT'),
                                   discount_amount DOUBLE(10,2),
                                   start_date DATE,
                                   end_date DATE,
                                   active BOOLEAN DEFAULT TRUE,
                                   FOREIGN KEY (product_id) REFERENCES products(id)
);
