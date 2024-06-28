CREATE TABLE IF NOT EXISTS users_roles(

    user_id BIGINT NOT NULL REFERENCES blog_users (id),
    role_id BIGINT NOT NULL REFERENCES role (id)
);