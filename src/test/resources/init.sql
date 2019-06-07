--init genre table
CREATE TABLE public.genre (
    id serial,
    name character varying COLLATE pg_catalog."default",
    CONSTRAINT genre_pkey1 PRIMARY KEY (id)
);

--init track table
CREATE TABLE public.track
(
    id serial,
    name character varying(200) COLLATE pg_catalog."default",
    genre_id integer,
    length bigint,
    release_date date,
    CONSTRAINT track_pkey1 PRIMARY KEY (id),
    CONSTRAINT foreign_key_genre_id FOREIGN KEY (genre_id)
        REFERENCES public.genre (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--init musician table
CREATE TABLE public.musician
(
    id serial,
    name character varying(200) COLLATE pg_catalog."default",
    is_singer boolean,
    is_author boolean,
    CONSTRAINT musician_pkey PRIMARY KEY (id)
);

--init singer_track table
CREATE TABLE public.singer_track
(
    track_id integer,
    singer_id integer,
    CONSTRAINT foreign_key_singer_id FOREIGN KEY (singer_id)
        REFERENCES public.musician (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT foreign_key_track_id FOREIGN KEY (track_id)
        REFERENCES public.track (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--init author_track table
CREATE TABLE public.author_track
(
    track_id integer,
    author_id integer,
    CONSTRAINT foreign_key_author_id FOREIGN KEY (author_id)
        REFERENCES public.musician (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT foreign_key_track_id FOREIGN KEY (track_id)
        REFERENCES public.track (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--init playlist table
CREATE TABLE public.playlist
(
    id serial,
    name character varying(200) COLLATE pg_catalog."default",
    CONSTRAINT playlist_pkey1 PRIMARY KEY (id)
);

--init playlist_track table
CREATE TABLE public.playlist_track
(
    playlist_id integer,
    track_id integer,
    CONSTRAINT foreign_key_playlist_id FOREIGN KEY (playlist_id)
        REFERENCES public.playlist (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT foreign_key_track_id FOREIGN KEY (track_id)
        REFERENCES public.track (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--init user table
CREATE TABLE public."user"
(
    id serial,
    login character varying(200) COLLATE pg_catalog."default",
    password character varying(200) COLLATE pg_catalog."default",
    is_admin boolean,
    first_name character varying(200) COLLATE pg_catalog."default",
    last_name character varying(200) COLLATE pg_catalog."default",
    "e-mail" character varying(200) COLLATE pg_catalog."default",
    gender boolean,
    date_of_birth date,
    CONSTRAINT user_pkey1 PRIMARY KEY (id),
    CONSTRAINT unic_login UNIQUE (login)

);

--init user_playlist table
CREATE TABLE public.user_playlist
(
    user_id integer,
    playlist_id integer,
    CONSTRAINT foreign_key_playlist_id FOREIGN KEY (playlist_id)
        REFERENCES public.playlist (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT foreign_key_user_id FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT into "user" ("login", "password", is_admin, first_name, last_name, "e-mail", date_of_birth, gender)
values ('pldi', 'qwerty', true, 'Dima', 'Platonov', 'pldi@mail.ru', '1986-07-02', true),
    ('pldi1', 'qwerty', false, 'Yuliya', 'Platonava', 'yuliya@icloud.com', '1986-10-08', false),
    ('pldi2', 'qwerty', false, 'Miroslav', 'Platonov', 'miroslav@icloud.com', '2016-01-24', true),
    ('pldi3', 'qwerty', false, 'Zinedin', 'Zidane', 'zidane@gmail.com', '1975-10-10', true),
    ('pldi4', 'qwerty', false, 'Leo', 'Messi', 'messi@gmail.com', '1987-01-01', true);

insert into playlist ("name") values ('spring2019'), ('summer2019'), ('authum2019'), ('winter2019'), ('new year party mix');
insert into user_playlist (user_id, playlist_id) values (1, 2), (5, 1), (4, 3), (3, 4), (3, 1), (2, 5);
insert into genre ("name") values ('pop'), ('rock'), ('rap'), ('jazz'), ('funk'), ('retro'), ('chanson');
insert into track ("name", genre_id, release_date, "length")
values ('Tim', 1, '2019-01-01', 180),
    ('i_Suss', 7, '2019-02-02', 195),
    ('Властелин калек', 1, '2018-12-01', 200),
    ('Зацепила', 1, '2019-03-11', 185),
    ('Numb', 2, '2005-05-06', 211);
insert into playlist_track (playlist_id, track_id) values (1, 4), (5, 1), (5, 2), (5, 3), (5, 4);
insert into musician ("name", is_singer, is_author)
values ('Avici', true, false),
       ('Ленинград', true, false),
       ('Артур Пирожков', true, false),
       ('Saluki', true, false),
       ('Linkin Park', true, true),
       ('Bethowen', false, true),
       ('Филипп Киркоров', true, true);
insert into singer_track (track_id, singer_id) values (2, 2), (1, 1), (4, 3), (3, 4), (5, 5);
insert into author_track (track_id, author_id) values (2, 2), (1, 1), (4, 3), (3, 4), (5, 5);
