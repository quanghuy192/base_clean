set foreign_key_checks = 0;

drop table if exists cinema;
create table cinema (
  id bigint unsigned primary key auto_increment,
  cinema_name varchar(128) not null,
  gg_map varchar(255) not null,
  phone_number int not null
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci
;


drop table if exists movie;
create table movie (
  id bigint unsigned primary key auto_increment,
  title varchar(128) not null,
  _type varchar(64) not null,
  icon varchar(128) not null,
  link_trailer varchar(64) not null,
  time varchar(32) not null,
  director varchar(64) not null,
  actors varchar(128) not null,
  imdb_point int not null,
  day_start datetime not null default current_timestamp,
  content varchar(255) not null,
  _format varchar(16) not null
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci
;

set foreign_key_checks = 1;