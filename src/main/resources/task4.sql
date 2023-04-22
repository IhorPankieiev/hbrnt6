use library;

drop table if exists author;

drop table if exists book;

create table author(
id int not null auto_increment primary key,
name varchar(45),
last_name varchar(45) 
);

create table book(
id int not null auto_increment primary key,
title varchar(50)
);

create table author_book(
author_id int not null,
book_id int not null,
foreign key (author_id) references author(id),
foreign key(book_id) references book(id),
primary key (author_id, book_id)
);