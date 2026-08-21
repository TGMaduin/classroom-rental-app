CREATE DATABASE IF NOT EXISTS classroom_rental_db
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE classroom_rental_db;

CREATE TABLE IF NOT EXISTS customer (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phone VARCHAR(15);
    customer_type VARCHAR(20) NOT NULL,
    organization_number VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS booking_user (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,

    CONSTRAINT fk_booking_user_customer_id
        FOREIGN KEY (customer_id)
        REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS classroom (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    capacity INT NOT NULL CHECK (capacity > 0),
    disability_accessible BOOLEAN NOT NULL,
);

CREATE TABLE IF NOT EXISTS booking (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    booking_user_id INT NOT NULL,
    classroom_id INT NOT NULL,
    start_date_time TIMESTAMP NOT NULL,
    end_date_time TIMESTAMP NOT NULL,
    comments VARCHAR(1000),

    CONSTRAINT fk_booking_customer_id
        FOREIGN KEY (customer_id)
        REFERENCES customer(id),

    CONSTRAINT fk_booking_classroom_id
        FOREIGN KEY (classroom_id)
        REFERENCES classroom(id),

    CONSTRAINT fk_booking_user
        FOREIGN KEY (booking_user_id)
        REFERENCES booking_user(id),

    CONSTRAINT check_booking_time
        CHECK (end_date_time > start_date_time)
);

CREATE TABLE IF NOT EXISTS classroom_equipment (
    classroom_id INT NOT NULL,
    equipment_type VARCHAR(50) NOT NULL,

    PRIMARY KEY (classroom_id, equipment_type),

    CONSTRAINT fk_classroom_equipment
        FOREIGN KEY (classroom_id)
        REFERENCES classroom(id)
);