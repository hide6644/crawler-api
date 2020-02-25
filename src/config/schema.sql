drop table if exists app_user cascade;
drop table if exists app_user_roles cascade;

create table app_user (
    username varchar(16) not null,
    password varchar(80) not null,
    email varchar(64) not null,
    enabled boolean not null,
    version integer not null,
    create_user varchar(16) default null,
    create_date timestamp not null default current_timestamp,
    update_user varchar(16) default null,
    update_date timestamp not null default current_timestamp on update current_timestamp,
    primary key (username),
    unique (email)
) engine = InnoDB default character set utf8;

create table app_user_roles (
    username varchar(16) not null,
    roles varchar(16) not null,
    primary key (username, roles)
) engine = InnoDB default character set utf8;
