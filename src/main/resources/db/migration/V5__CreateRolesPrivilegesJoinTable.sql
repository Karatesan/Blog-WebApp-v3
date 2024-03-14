CREATE TABLE IF NOT EXISTS roles_privileges(
    role_id BIGINT NOT NULL REFERENCES role (id),
    privilege_id BIGINT NOT NULL REFERENCES privilege (id)
);