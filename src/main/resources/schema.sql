CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255),
                      email VARCHAR(255),
                      password VARCHAR(255),
                      gender VARCHAR(50),
                      firstname VARCHAR(255),
                      lastname VARCHAR(255),
                      birthday DATE,
                      activation_key VARCHAR(255),
                      is_active BOOLEAN,
                      last_activity_at TIMESTAMP
);

CREATE TABLE role (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255)
);

CREATE TABLE user_roles (
                            user_id BIGINT,
                            roles_id BIGINT,
                            PRIMARY KEY (user_id, roles_id),
                            FOREIGN KEY (user_id) REFERENCES user(id),
                            FOREIGN KEY (roles_id) REFERENCES role(id)
);

