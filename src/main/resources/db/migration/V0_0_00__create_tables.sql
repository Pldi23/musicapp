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
alter table track add column created_at timestamp default now();

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
    private boolean,
    CONSTRAINT playlist_pkey1 PRIMARY KEY (id)
);

--init playlist_track table
CREATE TABLE public.playlist_track
(
    playlist_id integer,
    track_id integer,
    index bigint,
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
ALTER TABLE application_user add column paid_period TIMESTAMP;

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

--init payment table

CREATE TABLE payment
(
    id serial,
    amount numeric,
    card_number varchar(200),
    user_login varchar(200),
    created_at timestamp default now(),
    constraint payment_pkey1 PRIMARY KEY (id),
    constraint foreign_key_user_login foreign key (user_login) references application_user (login)

);