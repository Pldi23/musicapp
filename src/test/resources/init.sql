--init genre table
CREATE TABLE public.genre (
    id serial,
    genre_name character varying COLLATE pg_catalog."default",
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
alter table track add column uuid text;

--init musician table
CREATE TABLE public.musician
(
    id serial,
    name character varying(200) COLLATE pg_catalog."default",
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
CREATE TABLE public.application_user
(
    login character varying(200) COLLATE pg_catalog."default",
    password character varying(200) COLLATE pg_catalog."default",
    is_admin boolean,
    first_name character varying(200) COLLATE pg_catalog."default",
    last_name character varying(200) COLLATE pg_catalog."default",
    "e_mail" character varying(200) COLLATE pg_catalog."default",
    gender boolean,
    date_of_birth date,
    CONSTRAINT application_user_pkey1 PRIMARY KEY (login),
    CONSTRAINT unic_login UNIQUE (login)

);
ALTER TABLE application_user ADD COLUMN created_at TIMESTAMP DEFAULT NOW();
ALTER TABLE application_user ADD COLUMN active_status boolean DEFAULT false;
ALTER TABLE application_user ADD COLUMN verification_uuid character varying(200);
ALTER TABLE application_user add column photo_path character varying(200);

--init user_playlist table
CREATE TABLE public.user_playlist
(
    user_login character varying(200),
    playlist_id integer,
    CONSTRAINT foreign_key_playlist_id FOREIGN KEY (playlist_id)
        REFERENCES public.playlist (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT foreign_key_user_login FOREIGN KEY (user_login)
        REFERENCES public.application_user (login) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT into application_user ("login", "password", is_admin, first_name, last_name, "e_mail", date_of_birth, gender, active_status, verification_uuid, photo_path)
values ('pldi', 'qwerty', true, 'Dima', 'Platonov', 'pldi@mail.ru', '1986-07-02', true, false, null, null),
    ('pldi1', 'qwerty', false, 'Yuliya', 'Platonava', 'yuliya@icloud.com', '1986-10-08', false, false, null, null),
    ('pldi2', 'qwerty', false, 'Miroslav', 'Platonov', 'miroslav@icloud.com', '2016-01-24', true, false, null, null),
    ('pldi3', 'qwerty', false, 'Zinedin', 'Zidane', 'pldi@mail.ru', '1975-10-10', true, false, '1', '/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/photo/default_ava.png'),
    ('pldi4', 'qwerty', false, 'Leo', 'Messi', 'messi@gmail.com', '1987-01-01', true, false, '1', '/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/photo/default_ava.png');

insert into playlist ("name") values ('spring2019'), ('summer2019'), ('authum2019'), ('winter2019'), ('new year party mix');
insert into user_playlist (user_login, playlist_id) values ('pldi', 2), ('pldi4', 1), ('pldi3', 3), ('pldi2', 4), ('pldi1', 1), ('pldi1', 5);
insert into genre (genre_name)values ('pop'),('rock'),('rap'),('jazz'),('funk'),('retro'),('chanson');
insert into track ("name", genre_id, release_date, "length", uuid)
values ('Tim', 1, '2019-01-01', 180, '/users/dzmitryplatonov/Dropbox/music/avicii-tim.mp3'),
    ('i_Suss', 7, '2019-02-02', 195, '/users/dzmitryplatonov/Dropbox/music/leningrad_i_suss.mp3'),
    ('Властелин калек', 1, '2018-12-01', 200, '/users/dzmitryplatonov/Dropbox/music/saluki_vlastelin_kalek.mp3'),
    ('Зацепила', 1, '2019-03-11', 185, '/users/dzmitryplatonov/Dropbox/music/artur_pirozhkov_zacepila.mp3'),
    ('Numb', 2, '2005-05-06', 211, '/users/dzmitryplatonov/Dropbox/music/linkin-park-numb.mp3'),
    ('Duet', 3, '2019-01-06', 201, '');
insert into playlist_track (playlist_id, track_id) values (1, 4), (5, 1), (5, 2), (5, 3), (5, 4);
insert into musician ("name")
values ('Avici'),
       ('Ленинград'),
       ('Артур Пирожков'),
       ('Saluki'),
       ('Linkin Park'),
       ('Bethowen'),
       ('Филипп Киркоров');
insert into singer_track (track_id, singer_id) values (2, 2), (1, 1), (4, 3), (3, 4), (5, 5), (6, 3), (6, 7);
insert into author_track (track_id, author_id) values (2, 2), (1, 1), (4, 3), (3, 4), (5, 5), (6, 7), (6, 6);
