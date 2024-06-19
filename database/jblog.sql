SELECT * FROM jblog.user;

select * from blog;
select * from category;
select * from post;

select no, title, contents from post where no=5 and category_no=10000;


delete from post where category_no=7;
-- delete from category where no= and id='jiwoo';

select id, name from user where id = 'jiwoo' and password = '1234';

insert into user values('jiwoo', '지우', password('1234'), current_date());
insert into category values(null, '미분류', null, current_date(), 'aa');
insert into blog values('aa', 'aa님의 블로그', '로고');
insert into post values(null,'블로그 제목','내용',now(),1);

select no, name from category where id='jiwoo' order by name;
select id, title, logo from blog where id= 'jiwoo';
select no from post where category_no=5 order by reg_date desc limit 1;
select no from category where id='jiwoo' order by no desc limit 1;

select no, title, reg_date, category_no from post where category_no=5 order by reg_date desc;

-- join
select a.no as no, title, contents from post a, category b where a.category_no=b.no and a.no=1 and b.id='jiwoo' group by category_no;

-- admin 글작성
select no, name from category where id = 'jiwoo';

-- admin category 별 post 개수 list
select a.no as no, name, count(b.no) as postCount, description from category a left join post b on a.no=b.category_no where a.id='jiwoo' group by a.no order by a.no desc;

-- 스프링 캠프에서는 JVM(Java Virtual Machine) 기반 시스템의 백엔드(Back-end) 또는 서버사이드(Server-side)라고 칭하는 영역을 개발하는 애플리케이션 서버 개발에 관한 기술과 정보, 경험을 공유하는 컨퍼런스입니다.
-- 핵심주제로 Java와 Spring IO Platform을 다루고 있으며, 그외 Architecture나 JVM Language, Software Development Process 등 애플리케이션 서버 개발에 필요한 다양한 주제를 다루려고 노력하고 있습니다.
-- 우리는 같은 일을 하고, 같은 관심사를 가진 개발자들이지만 서로를 모릅니다.
-- 스프링 캠프라는 컨퍼런스에 찾아온 낯선 개발자들 사이에서 자신을 소개하고 이야기를 나누고 웃고 즐기며면서 어색함을 떨쳐내고 우리가 같은 분야에서 함께 일하고 있는 친구이자 동료라는 것을 인지하고 새로운 인연의 고리를 연결하고 이어갈 수 있는 순간으로 만들어가려 합니다.