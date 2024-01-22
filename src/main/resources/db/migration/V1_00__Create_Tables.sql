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
    price DOUBLE,
    category_id BIGINT NOT NULL,
    is_deleted BIT DEFAULT 0,
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
                                     user_id BIGINT NOT NULL,
                                     cart_status VARCHAR(45),
    is_deleted BIT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS cartlines (
                                         id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                         cart_id BIGINT NOT NULL,
                                         product_id BIGINT NOT NULL,
                                         quantity INT,
                                         FOREIGN KEY (cart_id) REFERENCES carts (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
    );

CREATE TABLE IF NOT EXISTS orders (
                                      id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                      status VARCHAR(45),
    cart_id BIGINT NOT NULL,
    date_created DATETIME,
    total INT,
    FOREIGN KEY (cart_id) REFERENCES carts (id)
    );