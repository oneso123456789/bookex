use book_ex;

create table tbl_reply(
rno int NOT NULL AUTO_INCREMENT,
bno int not null default 0,
replytext varchar(1000) not null,
replyer varchar(50) not null,
regdate TIMESTAMP not null default now(),
updatedate TIMESTAMP not null DEFAULT now(),
primary key(rno)
);

commit;

alter table tbl_reply add constraint fk_board
foreign key (bno) references tbl_board (bno);

select * from tbl_reply ;