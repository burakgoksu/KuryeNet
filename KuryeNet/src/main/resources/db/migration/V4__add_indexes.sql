CREATE INDEX IF NOT EXISTS idx_addresses_city ON addresses (city);
CREATE INDEX IF NOT EXISTS idx_addresses_address_title ON addresses (addres_title);
CREATE INDEX IF NOT EXISTS idx_addresses_phone_number ON addresses (phone_number);

CREATE INDEX IF NOT EXISTS idx_customers_email ON customers (email);
CREATE INDEX IF NOT EXISTS idx_customers_name_surname ON customers (name, surname);
CREATE INDEX IF NOT EXISTS idx_customers_address_id ON customers (address_id);

CREATE INDEX IF NOT EXISTS idx_couriers_email ON couriers (email);
CREATE INDEX IF NOT EXISTS idx_couriers_identity_number ON couriers (identity_number);
CREATE INDEX IF NOT EXISTS idx_couriers_address_id ON couriers (address_id);

CREATE INDEX IF NOT EXISTS idx_orders_order_number ON orders (order_number);
CREATE INDEX IF NOT EXISTS idx_orders_order_status ON orders (order_status);
CREATE INDEX IF NOT EXISTS idx_orders_order_date ON orders (order_date);
CREATE INDEX IF NOT EXISTS idx_orders_courier_id ON orders (courier_id);
CREATE INDEX IF NOT EXISTS idx_orders_address_id ON orders (address_id);
CREATE INDEX IF NOT EXISTS idx_orders_provider_id ON orders (provider_id);
CREATE INDEX IF NOT EXISTS idx_orders_customer_id ON orders (customer_id);

CREATE INDEX IF NOT EXISTS idx_customers_baskets_order_id ON customers_baskets (order_id);
CREATE INDEX IF NOT EXISTS idx_customers_baskets_customer_id ON customers_baskets (customer_id);

CREATE INDEX IF NOT EXISTS idx_providers_name ON providers (provider_name);
CREATE INDEX IF NOT EXISTS idx_providers_type ON providers (provider_type);
CREATE INDEX IF NOT EXISTS idx_providers_address_id ON providers (address_id);
CREATE INDEX IF NOT EXISTS idx_providers_mersis_no ON providers (mersis_no);

CREATE INDEX IF NOT EXISTS idx_vehicles_plate ON vehicles (vehicle_plate);
CREATE INDEX IF NOT EXISTS idx_vehicles_type ON vehicles (vehicle_type);
CREATE INDEX IF NOT EXISTS idx_vehicles_brand ON vehicles (vehicle_brand);
CREATE INDEX IF NOT EXISTS idx_vehicles_model ON vehicles (vehicle_model);
CREATE INDEX IF NOT EXISTS idx_vehicles_year ON vehicles (vehicle_year);

CREATE INDEX IF NOT EXISTS idx_users_email ON users (email);
CREATE INDEX IF NOT EXISTS idx_roles_name ON roles (role_name);
CREATE INDEX IF NOT EXISTS idx_users_roles_user_id ON users_roles (user_id);
CREATE INDEX IF NOT EXISTS idx_users_roles_role_id ON users_roles (role_id);
