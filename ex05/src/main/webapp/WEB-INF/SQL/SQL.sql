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

/*로그인 처리용 테이블*/
create table tbl_user(
	uid varchar(50) not null,
	upw varchar(50) not null,
	uname varchar(100) not null,
	upoint int not null default 0,
	primary key(uid)
);

create table tbl_message(
	mid int not null auto_increment,
	targetid varchar(50) not null,
	sender varchar(50) not null,
	message text not null,
	opendate timestamp,
	senddate timestamp not null default now(),
	primary key(mid)
);

alter table tbl_message add constraint fk_usertarget
foreign key (targetid) references tbl_user (uid); 


alter table tbl_message add constraint fk_usersender
foreign key (sender) references tbl_user (uid);

insert into tbl_user(uid, upw, uname) values ('user00','user00','IRON MAN');
insert into tbl_user(uid, upw, uname) values ('user01','user01','CAPTAIN');
insert into tbl_user(uid, upw, uname) values ('user02','user02','HULK');
insert into tbl_user(uid, upw, uname) values ('user03','user03','Thor');
insert into tbl_user(uid, upw, uname) values ('user10','user10','Quick Silver');


alter table tbl_board add column replycnt int default 0;
commit;

select * from tbl_board;
select * from tbl_board order by bno desc;

update tbl_board set replycnt =
(select count(rno)
from
 tbl_reply
 where bno = tbl_board.bno) where bno > 0;
 
delete from tbl_board where bno = 1535;
/* tbl_attach에서 첨부파일의 이름은 업로드 시점에 고유하게 처리되기 때문에, primary key(기본키)로 사용가능
 * 모든 첨부파일의 정보는 특정 게시물과 관련이 있으므로, bno 칼럼을 생성하고, 이는 외래키(foreign key)로 참조해서 사용
 * 이렇게 데이터베이스의 구조가 변경되기 때문에 등록 작업에도 트랜잭션 처리가 필요함
 * 게시물이 등록될 때 기존의 tbl_board에만 insert되는것이 아니라, tbl_attach라는 테이블에도 어떤 게시물이 어떤 첨부파일을 사용하게 되는지를 저장
*/
 create table tbl_attach(
 	fullName varchar(150) not null,
 	bno int not null,
 	regdate timestamp default now(),
 	primary key(fullName)
 );
 
 alter table tbl_attach add constraint fk_board_attach
 foreign key (bno) references tbl_board (bno);
 
 select * from tbl_attach;
 
 

 
 show tables ;
 
 commit;