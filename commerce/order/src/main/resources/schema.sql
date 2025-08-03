CREATE TABLE IF NOT EXISTS orders {
order_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
shopping_cart_id UUID,
payment_id UUID,
delivery_id UUID,
state VARCHAR(30) NOT NULL,
delivery_weight NUMERIC(8, 2) NOT NULL CHECK (width >= 1),
delivery_volume NUMERIC(8, 2) NOT NULL CHECK (width >= 1),
fragile BOOLEAN NOT NULL,
total_price NUMERIC(8, 2),
delivery_price NUMERIC(8, 2),
product_price NUMERIC(8, 2)
};

CREATE TABLE IF NOT EXISTS order_products {
id UUID PRIMARY KEY,
order_id UUID NOT NULL,
product_id UUID NOT NULL,
quantity BIGINT NOT NULL,
FOREIGN KEY (order_id) REFERENCES orders(order_id)
};