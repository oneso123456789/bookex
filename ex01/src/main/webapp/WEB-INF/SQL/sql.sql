
use book_ex;



drop table tbl_board;

create table tbl_board(
	bno INT NOT NULL AUTO_INCREMENT,
	title varchar(200) not null,
	content text null,
	writer varchar(50) not null,
	regdate timestamp not null default now(),
	viewcnt int default 0,
	primary key (bno));
	
	select * from tbl_board;
	commit;
	
   새로운 게시물의 등록데 사용하는 SQL
	insert into tbl_board(title, content, writer)
	values('제목입니다','내용입니다','user00');

	commit;
	
# 게시물의 조회에 사용하는 SQL
select * from tbl_board ;

# 게시물의 전체 목록에 사용하는 SQL
select * from tbl_board where bno > 0 order by bno desc;

select * from tbl_board where title = "나르" ; 

# 게시물의 수정에 사용하는 SQL
update tbl_board set title='수정된 제목' where bno = 1;

#게시물의 삭제에 사용하는 SQL
delete from tbl_board where bno = 1;

insert into tbl_board (title, content, writer)
(select title, content, writer from tbl_board);