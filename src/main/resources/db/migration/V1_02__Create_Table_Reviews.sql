CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    rating INT NOT NULL,
    comment VARCHAR(255),
    created_at DATETIME,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

