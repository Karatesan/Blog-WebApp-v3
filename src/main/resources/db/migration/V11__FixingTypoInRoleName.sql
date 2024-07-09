DELETE FROM roles_privileges
USING role
WHERE roles_privileges.role_id = role.id
AND role.name = 'PREACTIVATED_USER';

DELETE FROM role
WHERE role.name = 'PREACTIVATED_USER';

INSERT INTO role (name) VALUES ('ROLE_PREACTIVATED');

INSERT INTO roles_privileges (role_id, privilege_id)
VALUES
( (SELECT id FROM role WHERE name = 'ROLE_PREACTIVATED'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE'))