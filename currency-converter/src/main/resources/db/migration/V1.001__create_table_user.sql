CREATE TABLE users (

  id UUID PRIMARY KEY,
  username VARCHAR(128) NOT NULL,
  password VARCHAR(128) NOT NULL
);

CREATE TABLE user_role (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    role VARCHAR(64) NOT NULL,
    CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES users(id)
  );

INSERT INTO users (id, username, password) VALUES ('3884bd3f-93d8-4df3-8241-8d8676dd3cad', 'Admin', '83cb40d1beaba8036da4c2cf2e62fd8d54557afb1c136f281eb27e1086519bf31c7de995b0211e34');
INSERT INTO user_role (id, user_id, role) VALUES ('cb53077a-0652-4134-bcc2-e4e179c248d6', '3884bd3f-93d8-4df3-8241-8d8676dd3cad', 'ADMIN');