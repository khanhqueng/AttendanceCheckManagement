INSERT INTO role (id, name)
VALUES
    (1, 'MANAGER'),
    (2, 'TEACHER'),
    (3, 'STUDENT')
ON DUPLICATE KEY UPDATE
id = VALUES(id);

INSERT IGNORE INTO user (id, role_id, password, username)
VALUES (2, 1, '$2a$10$7m9bdD49Aei2O0SVHY9Tge5P4MiyKbv6V4EFsDQaNWXBxpmzxJoQG', 'manager');

INSERT IGNORE INTO manager (id, dob, user_id, email, name, phone_number)
VALUES (1, '1990-05-15', 2, 'manager@gm.uit.edu.vn', 'Nguyen Thi Hoa', '0964321467');


