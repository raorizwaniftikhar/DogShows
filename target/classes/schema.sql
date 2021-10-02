CREATE TABLE role (
  id    BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name  varchar(255) NOT NULL UNIQUE
);
CREATE TABLE user (
  id          BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email       varchar(255) NOT NULL UNIQUE,
  first_name  varchar(255) NOT NULL,
  last_name   varchar(255) NOT NULL,
  password    varchar(255) NOT NULL,
  image_link  varchar(255) NULL
);
CREATE TABLE users_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL
);
CREATE TABLE breed (
  id          BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name              varchar(255) NOT NULL
);
CREATE TABLE dog (
  id            BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  breed_id      BIGINT NOT NULL,
  name          varchar(255) NOT NULL,
  gender        varchar(255) NOT NULL,
  specialty     varchar(255) NOT NULL,
  user_id       BIGINT NOT NULL
);

ALTER TABLE users_roles
  ADD CONSTRAINT users_roles_fk1 FOREIGN KEY (user_id)
  REFERENCES user (id);
ALTER TABLE users_roles
  ADD CONSTRAINT users_roles_fk2 FOREIGN KEY (role_id)
  REFERENCES role (id);

ALTER TABLE dog
  ADD CONSTRAINT breed_fk1 FOREIGN KEY (breed_id)
  REFERENCES breed (id) ;

ALTER TABLE dog
  ADD CONSTRAINT user_fk1 FOREIGN KEY (user_id)
  REFERENCES user (id);