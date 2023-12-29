
create table if not exists roles
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    name
    varchar
(
    50
) not null
    );


create table if not exists users
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    date_of_birth
    varchar
(
    20
),
    email varchar
(
    100
) not null unique,
    full_name varchar
(
    50
) not null,
    username varchar
(
    50
) not null unique,
    password varchar
(
    50
) not null,
    phone_number varchar
(
    15
) not null,
    avatar varchar
(
    50
),
    is_deleted bit default 0,
    constraint users_uk unique
(
    username,
    email,
    phone_number
)
    );

create table if not exists addresses
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    user_id
    bigint,
    street
    varchar
(
    45
),
    city varchar
(
    45
),
    district varchar
(
    45
),
    ward varchar
(
    45
),
    is_default bit default 0,
    constraint user_id_fk foreign key
(
    user_id
) references users
(
    id
)
    );

create table if not exists users_roles
(
    user_id
    bigint
    not
    null,
    role_id
    bigint
    not
    null,
    constraint
    users_roles_pk
    primary
    key
(
    user_id,
    role_id
),
    constraint users_roles_roles_fk foreign key
(
    role_id
) references roles
(
    id
),
    constraint users_roles_users_fk foreign key
(
    user_id
) references users
(
    id
)
    );

create table if not exists categories
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    category_name
    varchar
(
    255
)
    );

create table if not exists products
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    product_name
    varchar
(
    255
),
    price double,
    category_id bigint not null,
    is_deleted bit default 0,
    constraint category_id_fk foreign key
(
    category_id
) references categories
(
    id
)
    );

create table if not exists product_images
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    url
    varchar
(
    255
),
    product_id bigint not null,
    constraint product_id_fk foreign key
(
    product_id
) references products
(
    id
)
    );

CREATE TABLE IF NOT EXISTS specification_templates
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    category_id
    BIGINT
    NOT
    NULL,
    spec_key
    VARCHAR
(
    255
) NOT NULL,
    UNIQUE KEY
(
    category_id,
    spec_key
),
    FOREIGN KEY
(
    category_id
) REFERENCES categories
(
    id
)
    );


CREATE TABLE IF NOT EXISTS specifications
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    product_id
    BIGINT
    NOT
    NULL,
    template_id
    BIGINT
    NOT
    NULL,
    spec_value
    VARCHAR
(
    255
) NOT NULL,
    FOREIGN KEY
(
    product_id
) REFERENCES products
(
    id
),
    FOREIGN KEY
(
    template_id
) REFERENCES specification_templates
(
    id
)
    );

CREATE TABLE IF NOT EXISTS product_details
(
    id
    BIGINT
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    product_id
    BIGINT
    NOT
    NULL,
    specification_id
    BIGINT
    NOT
    NULL,
    description
    VARCHAR
(
    255
),
    CONSTRAINT specification_id_fk FOREIGN KEY
(
    specification_id
) REFERENCES specifications
(
    id
),
    CONSTRAINT products_id_fk FOREIGN KEY
(
    product_id
) REFERENCES products
(
    id
)
    );


create table if not exists carts
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    user_id
    bigint
    not
    null,
    cart_status
    varchar
(
    45
),
    is_deleted bit default 0,
    foreign key
(
    user_id
) references users
(
    id
)
    );


create table if not exists cartlines
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    cart_id
    bigint
    not
    null,
    product_id
    bigint
    not
    null,
    quantity
    int,
    foreign
    key
(
    cart_id
) references carts
(
    id
),
    foreign key
(
    product_id
) references products
(
    id
)
    );


create table if not exists orders
(
    id
    bigint
    not
    null
    primary
    key
    auto_increment,
    status
    varchar
(
    45
),
    cart_id bigint not null,
    date_created datetime,
    total int,
    foreign key
(
    cart_id
) references carts
(
    id
)
    );
