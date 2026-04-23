-- =========================
-- RESET DB (DEV ONLY)
-- =========================
-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;
-- SET search_path TO public;

-- =========================
-- USERS
-- =========================
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100),
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) CHECK (role IN ('USER', 'ADMIN')) DEFAULT 'USER',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE guests (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        phone VARCHAR(20),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- ROOM TYPES
-- =========================
CREATE TABLE room_types (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            price NUMERIC(10,2) CHECK (price > 0),
                            max_capacity INT CHECK (max_capacity > 0),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- ROOMS
-- =========================
CREATE TABLE rooms (
                       id SERIAL PRIMARY KEY,
                       room_type_id INT REFERENCES room_types(id) ON DELETE CASCADE,
                       room_number VARCHAR(50) UNIQUE NOT NULL,
                       status VARCHAR(20) CHECK (status IN ('AVAILABLE','OCCUPIED','MAINTENANCE')) DEFAULT 'AVAILABLE',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- AMENITIES
-- =========================
CREATE TABLE amenities (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           icon VARCHAR(255),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- ROOM TYPE ↔ AMENITIES
-- =========================
CREATE TABLE room_type_amenities (
                                     room_type_id INT REFERENCES room_types(id) ON DELETE CASCADE,
                                     amenity_id INT REFERENCES amenities(id) ON DELETE CASCADE,
                                     PRIMARY KEY (room_type_id, amenity_id)
);

-- =========================
-- BOOKINGS
-- =========================
CREATE TABLE bookings (
                          id SERIAL PRIMARY KEY,
                          user_id INT REFERENCES users(id) ON DELETE SET NULL,
                          room_id INT REFERENCES rooms(id) ON DELETE CASCADE,
                          check_in DATE NOT NULL,
                          check_out DATE NOT NULL,
                          number_of_guests INT CHECK (number_of_guests > 0),
                          status VARCHAR(20) CHECK (status IN ('PENDING','CONFIRMED','CANCELLED')) DEFAULT 'PENDING',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CHECK (check_out > check_in)
);

-- =========================
-- INDEXES
-- =========================
CREATE INDEX idx_booking_room ON bookings(room_id);
CREATE INDEX idx_booking_user ON bookings(user_id);
CREATE INDEX idx_booking_date ON bookings(check_in, check_out);

-- =========================
-- SEED DATA
-- =========================

-- USERS
INSERT INTO users (name, email, password, role) VALUES
                                                    ('Admin', 'admin@gmail.com', '$2a$10$hashhashhash', 'ADMIN'),
                                                    ('An Tran', 'an@gmail.com', '$2a$10$hashhashhash', 'USER'),
                                                    ('Nam Nguyen', 'nam@gmail.com', '$2a$10$hashhashhash', 'USER');

-- ROOM TYPES
INSERT INTO room_types (name, description, price, max_capacity) VALUES
                                                                    ('Standard', 'Basic room', 20.00, 2),
                                                                    ('Deluxe', 'Deluxe room with nice view', 50.00, 4),
                                                                    ('VIP', 'Luxury VIP room', 120.00, 6);

-- ROOMS (KHÔNG hardcode ID)
-- Standard
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'S101' FROM room_types WHERE name = 'Standard';
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'S102' FROM room_types WHERE name = 'Standard';
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'S103' FROM room_types WHERE name = 'Standard';
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'S104' FROM room_types WHERE name = 'Standard';
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'S105' FROM room_types WHERE name = 'Standard';

-- Deluxe
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'D201' FROM room_types WHERE name = 'Deluxe';
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'D202' FROM room_types WHERE name = 'Deluxe';
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'D203' FROM room_types WHERE name = 'Deluxe';

-- VIP
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'V301' FROM room_types WHERE name = 'VIP';
INSERT INTO rooms (room_type_id, room_number)
SELECT id, 'V302' FROM room_types WHERE name = 'VIP';

-- AMENITIES
INSERT INTO amenities (name) VALUES
                                 ('Wifi'),
                                 ('TV'),
                                 ('Air Conditioner'),
                                 ('Mini Bar'),
                                 ('Balcony');

-- ROOM TYPE AMENITIES (SAFE)
INSERT INTO room_type_amenities
SELECT rt.id, a.id
FROM room_types rt, amenities a
WHERE rt.name = 'Standard' AND a.name IN ('Wifi','TV');

INSERT INTO room_type_amenities
SELECT rt.id, a.id
FROM room_types rt, amenities a
WHERE rt.name = 'Deluxe' AND a.name IN ('Wifi','TV','Air Conditioner','Balcony');

INSERT INTO room_type_amenities
SELECT rt.id, a.id
FROM room_types rt, amenities a
WHERE rt.name = 'VIP' AND a.name IN ('Wifi','TV','Air Conditioner','Mini Bar','Balcony');

-- BOOKINGS (100% SAFE - KHÔNG HARD CODE ID)
INSERT INTO bookings (user_id, room_id, check_in, check_out, number_of_guests, status)
SELECT u.id, r.id, '2026-04-01', '2026-04-03', 2, 'CONFIRMED'
FROM users u
         JOIN rooms r ON r.room_number = 'D201'
WHERE u.email = 'an@gmail.com';

INSERT INTO bookings (user_id, room_id, check_in, check_out, number_of_guests, status)
SELECT u.id, r.id, '2026-04-02', '2026-04-05', 3, 'PENDING'
FROM users u
         JOIN rooms r ON r.room_number = 'D202'
WHERE u.email = 'nam@gmail.com';