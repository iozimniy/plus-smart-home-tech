CREATE SCHEMA IF NOT EXISTS store;

SET search_path TO store;

CREATE TABLE IF NOT EXISTS products (
id UUID PRIMARY KEY,
name VARCHAR(255),
description(350) VARCHAR,
image VARCHAR(255),
quantity_state VARCHAR,
product_state VARCHAR,
product_category VARCHAR,
price NUMERIC(8, 2) NOT NULL CHECK (price >= 1)
)