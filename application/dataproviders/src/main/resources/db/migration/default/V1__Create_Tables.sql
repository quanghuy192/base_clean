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
  m_type varchar(64) not null,
  icon varchar(128) not null,
  link_trailer varchar(128) not null,
  time varchar(32) not null,
  director varchar(64) not null,
  actors varchar(128) not null,
  imdb_point varchar(5),
  day_start varchar(16),
  content varchar(255) not null,
  `format` varchar(255) not null,
  crawled_at    datetime                           null,
  created_at    datetime default CURRENT_TIMESTAMP not null,
  updated_at    datetime default CURRENT_TIMESTAMP not null
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci
;

set foreign_key_checks = 1;