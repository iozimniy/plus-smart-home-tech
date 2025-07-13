CREATE TABLE IF NOT EXISTS cart (
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
username VARCHAR(255) NOT NULL,
active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS cart_products (
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
cart_id UUID,
product_id UUID,
quantity BIGINT
FOREIGN KEY (cart_id) REFERENCES cart(id)
);