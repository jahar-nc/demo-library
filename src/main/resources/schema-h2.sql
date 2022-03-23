create table if not exists book (
	book_id bigint generated always as identity,
	title text not null,
	genre text not null,
	author text not null
);
