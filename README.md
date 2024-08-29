# Assignment_Android

script sql
```sql
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fName VARCHAR(100) NOT NULL,
    lName VARCHAR(100) NOT NULL,
    phone VARCHAR(10) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (phone, fName, lName, password, email) VALUES
('0123456789', 'Nguyen Van', 'A', 'password', NULL),
('0111222343', 'Nguyen Van', 'B', 'password2', 'email2@gmail.com');

INSERT INTO transactions (user_id, category, description, amount) VALUES
(1, 'Thu', 'Lương tháng', 15000000),
(1 , 'Chi', 'Mua sắm quần áo', 2000000),
(2, 'Chi', 'Ăn uống', 500000),
(1, 'Chi', 'Thanh toán hóa đơn điện', 30000),
(2, 'Chi', 'Lãi suất tiền gửi', 300000);

SELECT * FROM transactions;
SELECT * FROM users;

SELECT * FROM transactions A JOIN users B ON B.id = A.user_id WHERE B.phone = '0123456789'
```
