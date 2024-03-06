DROP TABLE IF EXISTS mpa CASCADE;
CREATE TABLE IF NOT EXISTS mpa
(
    id          INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR NOT NULL,
    description VARCHAR
);

DROP TABLE IF EXISTS genres CASCADE;
CREATE TABLE IF NOT EXISTS genres
(
    id   INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR NOT NULL
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users
(
    id       INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR,
    login    VARCHAR NOT NULL,
    email    VARCHAR,
    birthday TIMESTAMP
);

DROP TABLE IF EXISTS films CASCADE;
CREATE TABLE IF NOT EXISTS films
(
    id           INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR NOT NULL,
    description  VARCHAR,
    release_date TIMESTAMP,
    duration     INTEGER,
    mpa          VARCHAR
);

DROP TABLE IF EXISTS friendships CASCADE;
CREATE TABLE IF NOT EXISTS friendships
(
    id         INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    INTEGER NOT NULL REFERENCES users,
    friend_id  INTEGER NOT NULL REFERENCES users,
    is_approve INTEGER NOT NULL DEFAULT false
);

DROP TABLE IF EXISTS films_genres CASCADE;
CREATE TABLE IF NOT EXISTS films_genres
(
    id       INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    film_id  INTEGER NOT NULL REFERENCES films,
    genre_id INTEGER NOT NULL REFERENCES genres
);

DROP TABLE IF EXISTS likes CASCADE;
CREATE TABLE IF NOT EXISTS likes
(
    id      INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    film_id INTEGER NOT NULL REFERENCES films,
    user_id INTEGER NOT NULL REFERENCES users
);
