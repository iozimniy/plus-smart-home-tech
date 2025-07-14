CREATE TABLE IF NOT EXISTS products (
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
name VARCHAR(255),
description VARCHAR(350),
image VARCHAR(255),
quantity_state VARCHAR,
product_state VARCHAR,
product_category VARCHAR,
price NUMERIC(8, 2) NOT NULL CHECK (price >= 1)
);