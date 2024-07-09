INSERT INTO role (name) VALUES ('PREACTIVATED_USER');

INSERT INTO roles_privileges (role_id, privilege_id)
VALUES
( (SELECT id FROM role WHERE name = 'PREACTIVATED_USER'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE'))