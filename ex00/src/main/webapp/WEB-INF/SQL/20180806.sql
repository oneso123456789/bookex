use book_ex;
create table aa ;

create table tbl_member(
userid varchar(50) not null,
userpw varchar(50) not null,
username varchar(50) not null,
email varchar(100),
regdate timestamp default now(),
updatedate timestamp default now(),
primary key(userid)
);
commit;
show tables;
drop table tb1_member;

desc tbl_member;

show tables like 'tbl_member';