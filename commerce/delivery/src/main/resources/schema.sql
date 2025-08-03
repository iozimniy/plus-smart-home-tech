CREATE TABLE IF NOT EXISTS addresses {
address_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
country VARCHAR(150),
city VARCHAR(150),
street VARCHAR(150),
house VARCHAR(30),
flat VARCHAR(10)
};

CREATE TABLE IF NOT EXISTS deliveries {
delivery_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
from_address_id UUID,
to_address_id UUID,
order_id UUID NOT NULL,
state VARCHAR(30) NOT NULL,
FOREIGN KEY (from_address_id) REFERENCES addresses(address_id),
FOREIGN KEY (to_address_id) REFERENCES addresses(address_id),
};