drop table if exists user_novel_info cascade;
drop table if exists app_user_roles cascade;
drop table if exists app_user cascade;

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

create table user_novel_info (
    id integer not null auto_increment,
    username varchar(16) not null,
    novel_id integer not null,
    favorite boolean default null,
    rank integer default null,
    version integer default null,
    create_user varchar(16) default null,
    create_date timestamp,
    update_user varchar(16) default null,
    update_date timestamp,
    primary key (id),
    foreign key (novel_id) references novel (id)
) engine = InnoDB default character set utf8;
