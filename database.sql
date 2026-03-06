CREATE DATABASE IF NOT EXISTS gd;
use gd;
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    phone VARCHAR(15),
    address VARCHAR(100),
    pincode VARCHAR(10),
    password VARCHAR(50)
);
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    price DOUBLE,
    image_path VARCHAR(100)
);
INSERT INTO products (name, price, image_path) VALUES
('Rice', 100, 'images/rice.png'),
('Potato', 120, 'images/potato.png'),
('Beet Root', 70, 'images/br.png'),
('Strawberry', 145, 'images/straw.jpg'),
('Green Peas', 120, 'images/peas.png'),
('Sweet Corn', 130, 'images/corn.png'),
('Butter', 140, 'images/butter.png'),
('Onion', 190, 'images/onion.png'),
('Tomato', 60, 'images/tomatos.jpg'),
('Carrot', 80, 'images/carrot.png');
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(50),
    quantity INT,
    price DOUBLE
); 
