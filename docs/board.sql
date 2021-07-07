desc board;

desc guestbook;
select * from user;
select * from board;

select * from board order by reg_date desc, group_no desc, order_no asc;

select a.no, a.title, a.contents, a.reg_date, a.hit, a.group_no, a.order_no, a.depth, a.user_no, b.name from board a, user b where a.user_no = b.no;

select max(group_no)+1 from board;
insert into board values(null, "하위","하위","2021-05-31",0,ifnull((select max(group_no)+1 from board as b),0),0,0,2);

update board set title ="벌써다섯시반", contents="다섯시반이다" where no = 6;

drop table board;

insert into site values ("MySite", "안녕하세요. 유니경의 mysite에 오신 것을 환영합니다.",  "assets/images/profile.png", "이 사이트는 웹 프로그래밍 실습과제 예제 사이트입니다.\n 메뉴는 사이트 소개, 방명록, 게시판이 있구요. Java 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.");