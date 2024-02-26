INSERT INTO MPA (NAME, DESCRIPTION)
VALUES ('G', 'У фильма нет возрастных ограничений'),
       ('PG', 'Детям рекомендуется смотреть фильм с родителями'),
       ('PG-13', 'Детям до 13 лет просмотр не желателен'),
       ('R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
       ('NC-17', 'Лицам до 18 лет просмотр запрещён');
SELECT *
FROM MPA;

INSERT INTO GENRES (NAME)
VALUES ('Комедия'),
       ('Драма'),
       ('Мультфильм'),
       ('Триллер'),
       ('Документальный'),
       ('Боевик');
SELECT *
FROM GENRES;

-- INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA)
-- VALUES ('Хищник', 'Динамичное кино', '1985-01-01', 100, SELECT mp.ID
--                                                         FROM MPA mp
--                                                         WHERE mp.NAME = 'PG-13');
--
-- INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA)
-- VALUES ('Появляется дракон', 'Драки', '1986-05-15', 100, SELECT mp.ID
--                                                          FROM MPA mp
--                                                          WHERE mp.NAME = 'PG-13');
--
-- INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA)
-- VALUES ('Унесённые ветром', 'Мелодрама', '1980-01-01', 200, SELECT mp.ID
--                                                             FROM MPA mp
--                                                             WHERE mp.NAME = 'PG');
-- SELECT *
-- FROM FILMS;
--
-- INSERT INTO USERS (NAME, LOGIN, EMAIL, BIRTHDAY)
-- VALUES ('Первый', 'firstuser', 'first@first.ru', '1985-03-02'),
--        ('Второй', 'seconduser', 'second@second.ru', '1986-04-26'),
--        ('Третий', 'thirdduser', 'third@third.ru', '1989-02-15');
-- SELECT *
-- FROM USERS;
--
--
-- INSERT INTO LIKES (FILM_ID, USER_ID)
-- VALUES (1, 2),
--        (2, 2),
--        (2, 1),
--        (2, 3),
--        (3, 1),
--        (3, 2);
-- SELECT * FROM LIKES;
--
-- INSERT INTO FILMS_GENRES (FILM_ID, GENRE_ID)
-- VALUES (1, 1),
--        (1, 2),
--        (1, 3),
--        (1, 4),
--        (2, 5),
--        (2, 6),
--        (2, 1),
--        (3, 2),
--        (3, 3),
--        (3, 4),
--        (3, 5),
--        (3, 6);
-- SELECT * FROM FILMS_GENRES;
--
-- INSERT INTO FRIENDSHIPS (USER_ID, FRIEND_ID, IS_APPROVE)
-- VALUES (1, 2, true),
--        (1, 3, true),
--        (2, 1, true),
--        (2, 3, true),
--        (3, 1, true),
--        (3, 2, true);
-- SELECT * FROM LIKES;
