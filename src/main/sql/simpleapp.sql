CREATE TABLE IF NOT EXISTS "users" (
    id              BIGSERIAL       NOT NULL PRIMARY KEY,
    nickname        VARCHAR(255)    NOT NULL,
    email           VARCHAR(155)    NOT NULL,
    pass            VARCHAR(255)    NOT NULL,
    creation_time   TIMESTAMP       NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT nickname_unique_index UNIQUE (nickname);
ALTER TABLE users
    ADD CONSTRAINT email_user_unique_index UNIQUE (email);

--- admindummy
INSERT INTO users(nickname, email, pass, creation_time) VALUES ('dummy', 'dummy@mail.com', 'c1a9df945e296ab46599844756d2110e21b17c85', CURRENT_TIMESTAMP);

CREATE TABLE IF NOT EXISTS "product" (
    id              BIGSERIAL       NOT NULL PRIMARY KEY,
    name            VARCHAR(160)    NOT NULL,
    description     TEXT            NOT NULL,
    size            VARCHAR(4)      NULL,
    color           VARCHAR(20)     NULL,
    stock           INT             NOT NULL DEFAULT (1),
    price           VARCHAR(50)     NOT NULL,
    creation_time   TIMESTAMP       NOT NULL
);

ALTER TABLE product
    ADD CONSTRAINT product_name_unique_index UNIQUE (name);

CREATE TABLE IF NOT EXISTS "coupon" (
    id              BIGSERIAL       NOT NULL PRIMARY KEY,
    coupon_code     VARCHAR(10)     NOT NULL,
    description     VARCHAR(150)    NOT NULL,
    valid           INT             NOT NULL DEFAULT (1), -- 0: Invalid, 1: Valid
    price           VARCHAR(255)    NOT NULL,
    creation_time   TIMESTAMP       NOT NULL
);

ALTER TABLE coupon
  ADD CONSTRAINT coupon_code_unique_index UNIQUE (coupon_code);

CREATE TABLE IF NOT EXISTS "cart" (
    id              BIGSERIAL       NOT NULL PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    coupon_code     VARCHAR(10)     NULL,
    price           VARCHAR(55)     NOT NULL,
    creation_time   TIMESTAMP       NOT NULL
);

ALTER TABLE cart
    ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS "cart_detail" (
    id              BIGSERIAL       NOT NULL PRIMARY KEY,
    cart_id         BIGINT          NOT NULL,
    product_id      BIGINT          NOT NULL,
    total           INT             NOT NULL DEFAULT (1)
);

ALTER TABLE cart_detail
    ADD FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE;
ALTER TABLE cart_detail
    ADD FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE;
