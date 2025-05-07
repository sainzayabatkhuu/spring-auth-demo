INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');


INSERT INTO user (
    id, username, email, password, gender, firstname, lastname,
    birthday, activation_key, is_active, last_activity_at
) VALUES (
             1, 'john_doe', 'john.doe@example.com', 'encrypted_password_123', 'Male',
             'John', 'Doe', '1990-05-20', 'abc123', true, NOW()
         );
