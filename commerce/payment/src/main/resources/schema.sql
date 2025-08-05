CREATE TABLE IF NOT EXISTS payments {
payment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
order_id UUID NOT NULL,
total NUMERIC(8, 2),
products_total NUMERIC(8, 2),
delivery_total NUMERIC(8, 2),
fee_total NUMERIC(8, 2),
state VARCHAR(30)
};