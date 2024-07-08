INSERT INTO privilege (name) VALUES ('READ_PRIVILEGE'), ('WRITE_PRIVILEGE'), ('COMMENT_PRIVILEGE'), ('DELETE_PRIVILEGE'), ('ADMIN_PRIVILEGE');

INSERT INTO role (name) VALUES ('ROLE_USER'), ('ROLE_AUTHOR'), ('ROLE_ADMIN');

INSERT INTO roles_privileges (role_id, privilege_id)
VALUES
((SELECT id FROM role WHERE name = 'ROLE_USER'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE')),
((SELECT id FROM role WHERE name = 'ROLE_USER'),(SELECT id FROM privilege WHERE name = 'COMMENT_PRIVILEGE')),
((SELECT id FROM role WHERE name = 'ROLE_AUTHOR'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE')),
((SELECT id FROM role WHERE name = 'ROLE_AUTHOR'),(SELECT id FROM privilege WHERE name = 'WRITE_PRIVILEGE')),
((SELECT id FROM role WHERE name = 'ROLE_AUTHOR'),(SELECT id FROM privilege WHERE name = 'COMMENT_PRIVILEGE')),
((SELECT id FROM role WHERE name = 'ROLE_ADMIN'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE')),
((SELECT id FROM role WHERE name = 'ROLE_ADMIN'),(SELECT id FROM privilege WHERE name = 'COMMENT_PRIVILEGE')),
((SELECT id FROM role WHERE name = 'ROLE_ADMIN'),(SELECT id FROM privilege WHERE name = 'WRITE_PRIVILEGE')),
((SELECT id FROM role WHERE name = 'ROLE_ADMIN'),(SELECT id FROM privilege WHERE name = 'ADMIN_PRIVILEGE'));
