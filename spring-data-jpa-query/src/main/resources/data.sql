drop table if exists user CASCADE;
create table user (id bigint not null, name varchar(255), status int(11), email varchar(255) , primary key (id));
insert into user values (1, 'zhangsan', 1, '123@123.com');
insert into user values (2, 'lisi', 0, '456@456.com');
insert into user values (3, 'wangwu', 1, '789@789.com');
insert into user values (4, 'zhaoliu', 0, '1234@1234.com');
insert into user values (5, 'sunqi', 0, '5678@5678.com');