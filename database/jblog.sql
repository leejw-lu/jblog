SELECT * FROM jblog.user;

select * from blog;
select * from category;
select * from post;

insert into user values('jiwoo', '지우', password('1234'), current_date());
insert into category values(null, '미분류', null, current_date(), 'aa');
insert into blog values('aa', 'aa님의 블로그', '로고');

insert into post values(null,'블로그 제목','내용',now(),5);
