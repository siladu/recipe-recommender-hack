create table aisle(aisle_id VARCHAR(255), aisle VARCHAR(255));
create table orders(order_id VARCHAR(255), user_id VARCHAR(255), eval_set VARCHAR(255), order_number BIGINT, order_day_of_the_week BIGINT, order_hour_of_day BIGINT, days_since_prior_order BIGINT);
create table product(product_id VARCHAR(255), name VARCHAR(255), aisle_id VARCHAR(255), department_id VARCHAR(255));
create table orders_products_train(order_id VARCHAR(255), product_id VARCHAR(255), add_to_cart_order BIGINT, reordered BOOLEAN);
create table orders_products_prior(order_id VARCHAR(255), product_id VARCHAR(255), add_to_cart_order BIGINT, reordered BOOLEAN);
create table department(department_id VARCHAR(255), department VARCHAR(255));

CREATE INDEX orders_primary ON orders(order_id);
CREATE INDEX product_id_primary ON product(product_id);
CREATE INDEX orders_products_train_product_id_foreign ON orders_products_train(product_id);
CREATE INDEX orders_products_train_order_id_foreign ON orders_products_train(order_id);

CREATE INDEX orders_products_prior_product_id_foreign ON orders_products_prior(product_id);
CREATE INDEX orders_products_prior_order_id_foreign ON orders_products_prior(order_id);
CREATE INDEX orders_user ON orders(user_id);
CREATE INDEX products_name ON products(name);
