drop table if exists user CASCADE;
create table user (id bigint not null, name varchar(255), status smallint(6), primary key (id));
insert into user values (1, 'zhangsan', 1);
insert into user values (2, 'lisi', 0);
insert into user values (3, 'wangwu', 1);
insert into user values (4, 'zhaoliu', 0);
insert into user values (5, 'sunqi', 0);