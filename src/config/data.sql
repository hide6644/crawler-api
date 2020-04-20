INSERT INTO app_user VALUES ('admin','{bcrypt}$2a$10$ePH3WRsV3RpUUKV0wrTI9eOOkHI6TmYsh0ZE/UV9lbOXNoAa2heNe','admin@foo.bar',true,0,null,null,null,null);
INSERT INTO app_user VALUES ('user','{bcrypt}$2a$10$xw1Aw3J/kRpHTFzja0KJKeCMOG2Ju/sKabqUL/jiIl7D/KFkFmKJ6','user@foo.bar',true,0,null,null,null,null);

INSERT INTO app_user_roles VALUES ('admin','ROLE_ADMIN');
INSERT INTO app_user_roles VALUES ('admin','ROLE_USER');
INSERT INTO app_user_roles VALUES ('user','ROLE_USER');
