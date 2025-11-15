-- 1. drop old one
DROP DATABASE IF EXISTS todo;

-- 2. create database
CREATE DATABASE todo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE todo;

-- 3. user
CREATE TABLE `user` (
  id         BIGINT  AUTO_INCREMENT PRIMARY KEY,
  email      VARCHAR(255) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;

-- 4. todo
CREATE TABLE todo (
  id          BIGINT  AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  user_id     BIGINT  NOT NULL,
  status      TINYINT NOT NULL COMMENT '1=not start, 2=in progress, 3=complete',
  due_date    DATE NULL,
  description TEXT,
  create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_todo_user FOREIGN KEY (user_id) REFERENCES `user`(id)
) ENGINE = InnoDB;

-- 5. role
CREATE TABLE role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
	create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 6. user_role
CREATE TABLE user_role (
    user_id BIGINT,
    role_id BIGINT,
	create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- 7. permission
CREATE TABLE permission (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  todo_id         BIGINT NOT NULL,
  subject_type    TINYINT NOT NULL COMMENT '1=role, 2=user',
  subject_id      BIGINT NOT NULL,
  permission_type TINYINT NOT NULL COMMENT '1=view, 2=edit',
  create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_perm_todo FOREIGN KEY (todo_id) REFERENCES todo(id),
  UNIQUE KEY uk_perm (todo_id, subject_type, subject_id)
) ENGINE = InnoDB;