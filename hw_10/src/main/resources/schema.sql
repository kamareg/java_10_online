create table authors
(
    id         varchar(255) primary key not null,
    first_name varchar(255) null,
    last_name  varchar(255) null
);

create table books
(
    id    varchar(255) primary key not null,
    title varchar(255) null,
    year  int null
);

create table book_author
(
    book_id   varchar(255) not null,
    author_id varchar(255) not null,
    primary key (book_id, author_id),
    foreign key (book_id) references books (id),
    foreign key (author_id) references authors (id)
);