INSERT INTO role (id, name)
VALUES
    (1, 'MANAGER'),
    (2, 'TEACHER'),
    (3, 'STUDENT')
ON DUPLICATE KEY UPDATE
id = VALUES(id);