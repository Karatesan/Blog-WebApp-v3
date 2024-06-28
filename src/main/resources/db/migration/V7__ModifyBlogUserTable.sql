ALTER TABLE blog_users
ADD COLUMN status VARCHAR(255);

UPDATE blog_users SET status = 'APPROVED';