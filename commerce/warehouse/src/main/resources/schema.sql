CREATE TABLE IF NOT EXISTS products {
id UUID PRIMARY KEY,
fragile BOOLEAN NOT NULL,
width NUMERIC(8, 2) NOT NULL CHECK (width >= 1)
height NUMERIC(8, 2) NOT NULL CHECK (height >= 1)
depth NUMERIC(8, 2) NOT NULL CHECK (depth >= 1)
weight NUMERIC(8, 2) NOT NULL CHECK (weight >= 1)
quantity BIGINT NOT NULL
};

CREATE TABLE IF NOT EXISTS order_booking {
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
order_id UUID NOT NULL,
delivery_id UUID
};