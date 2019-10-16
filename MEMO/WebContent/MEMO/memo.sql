drop table MEMO;

create table  MEMO (
	id int not null auto_increment,
	title varchar(10) not null,
	content varchar(500) not null,
	memoDate varchar(20) not null,
	primary key (id)
) charset=utf8;

select * from  MEMO;