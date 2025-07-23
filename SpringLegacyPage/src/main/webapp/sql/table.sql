create table slpmember(
	mem_num number not null,
	id varchar2(12) unique not null,
	nick_name varchar2(30),
	auth number(1) default 2 not null, -- 0:탈퇴,1:정지,2:일반,9:관리
	constraint slpmember_pk primary key (mem_num)
);

create table slpmember_detail(
	mem_num number not null,
	name varchar2(30) not null,
	passwd varchar2(12) not null,
	phone varchar2(15) not null,
	email varchar2(50) not null,
	zipcode varchar2(5) not null,
	address1 varchar2(90) not null,
	address2 varchar2(90) not null,
	photo blob,
	photo_name varchar2(100),
	reg_date date default sysdate not null,
	modify_date date,
	constraint slpmember_detail_pk primary key (mem_num),
	constraint slpmember_detail_fk foreign key (mem_num) references slpmember (mem_num)
);

create sequence slpmember_seq;

-- 게시판
create table slpboard(
	board_num number not null,
	title varchar2(90) not null,
	content clob not null,
	hit number(9) default 0 not null,
	reg_date date default sysdate not null,
	modify_date date, 
	filename varchar2(100),
	ip varchar2(40) not null,
	mem_num number not null,
	constraint slpboard_pk primary key (board_num),
	constraint slpboard_fk foreign key (mem_num) references slpmember (mem_num);
);

create sequence slpboard_seq;

--부모글 좋아요
create table slpboard_fav(
 board_num number not null,
 mem_num number not null,
 constraint slpfav_fk1 foreign key (board_num)
                references slpboard (board_num),
 constraint slpfav_fk2 foreign key (mem_num)
                references slpmember (mem_num)
);

--댓글
create table slpboard_reply(
 re_num number not null,
 re_content varchar2(900) not null,
 re_date date default sysdate not null,
 re_mdate date,
 re_ip varchar2(40) not null,
 board_num number not null,
 mem_num number not null,
 constraint slpboard_reply_pk primary key (re_num),
 constraint slpboard_reply_fk1 foreign key (board_num)
                         references slpboard (board_num),
 constraint slpboard_reply_fk2 foreign key (mem_num)
                         references slpmember (mem_num)
);
create sequence slpreply_seq;




























