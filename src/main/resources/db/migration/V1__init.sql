
CREATE TABLE categories (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT
);

CREATE TABLE products (
  id BIGSERIAL PRIMARY KEY,
  sku VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price numeric(12,2) NOT NULL,
  stock integer NOT NULL,
  category_id BIGINT,
  CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE customers (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  phone VARCHAR(50)
);

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE
);

CREATE TABLE roles (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY(user_id, role_id),
  CONSTRAINT fk_ur_user FOREIGN KEY(user_id) REFERENCES users(id),
  CONSTRAINT fk_ur_role FOREIGN KEY(role_id) REFERENCES roles(id)
);

CREATE TABLE carts (
  id BIGSERIAL PRIMARY KEY,
  customer_id BIGINT,
  created_at TIMESTAMP,
  status VARCHAR(50),
  CONSTRAINT fk_cart_customer FOREIGN KEY(customer_id) REFERENCES customers(id)
);

CREATE TABLE cart_items (
  id BIGSERIAL PRIMARY KEY,
  cart_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity integer NOT NULL,
  unit_price numeric(12,2) NOT NULL,
  CONSTRAINT fk_cart FOREIGN KEY(cart_id) REFERENCES carts(id),
  CONSTRAINT fk_cart_product FOREIGN KEY(product_id) REFERENCES products(id)
);

CREATE TABLE orders (
  id BIGSERIAL PRIMARY KEY,
  customer_id BIGINT NOT NULL,
  created_at TIMESTAMP,
  status VARCHAR(50),
  total numeric(12,2),
  CONSTRAINT fk_order_customer FOREIGN KEY(customer_id) REFERENCES customers(id)
);

CREATE TABLE order_items (
  id BIGSERIAL PRIMARY KEY,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity integer NOT NULL,
  unit_price numeric(12,2) NOT NULL,
  CONSTRAINT fk_order FOREIGN KEY(order_id) REFERENCES orders(id),
  CONSTRAINT fk_order_product FOREIGN KEY(product_id) REFERENCES products(id)
);

CREATE TABLE payments (
  id BIGSERIAL PRIMARY KEY,
  order_id BIGINT,
  amount numeric(12,2),
  method VARCHAR(100),
  status VARCHAR(50),
  CONSTRAINT fk_payment_order FOREIGN KEY(order_id) REFERENCES orders(id)
);

CREATE TABLE notifications (
  id BIGSERIAL PRIMARY KEY,
  customer_id BIGINT,
  message TEXT,
  type VARCHAR(100),
  sent_at TIMESTAMP,
  read boolean DEFAULT false,
  CONSTRAINT fk_notification_customer FOREIGN KEY(customer_id) REFERENCES customers(id)
);

CREATE TABLE system_config (
  key VARCHAR(255) PRIMARY KEY,
  value TEXT
);
