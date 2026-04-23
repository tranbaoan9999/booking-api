INSERT INTO guests (name, email, password, phone) VALUES
                                                      ('Nguyen Van A', 'a@gmail.com', '$2a$10$abc123hashedpassword', '0901234567'),
                                                      ('Tran Thi B', 'b@gmail.com', '$2a$10$def456hashedpassword', '0912345678'),
                                                      ('Le Van C', 'c@gmail.com', '$2a$10$ghi789hashedpassword', '0923456789');

INSERT INTO bookings (guest_id, room_id, check_in, check_out, number_of_guests, status, updated_by)
VALUES
    (1, 1, '2026-04-01', '2026-04-03', 2, 'CONFIRMED', NULL),

    (2, 2, '2026-04-05', '2026-04-07', 3, 'PENDING', 1);


select * from
    CREATE TABLE guests (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE bookings DROP COLUMN IF EXISTS user_id;
ALTER TABLE bookings
    ADD COLUMN guest_id INT;

ALTER TABLE bookings
    ADD CONSTRAINT fk_booking_guest
        FOREIGN KEY (guest_id) REFERENCES guests(id) ON DELETE CASCADE;

ALTER TABLE bookings
    ALTER COLUMN guest_id SET NOT NULL;

ALTER TABLE bookings
    ADD COLUMN updated_by INT;

ALTER TABLE bookings
    ADD CONSTRAINT fk_booking_updated_by
        FOREIGN KEY (updated_by) REFERENCES users(id) ON DELETE SET NULL;

CREATE INDEX idx_booking_guest ON bookings(guest_id);
CREATE INDEX idx_booking_updated_by ON bookings(updated_by);