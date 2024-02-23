TRUNCATE TABLE "age_rating";
INSERT INTO "age_rating" ("age_rating", "description")
VALUES ('G', 'У фильма нет возрастных ограничений'),
       ('PG', 'Детям рекомендуется смотреть фильм с родителями'),
       ('PG-13', 'Детям до 13 лет просмотр не желателен'),
       ('R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
       ('NC-17', 'Лицам до 18 лет просмотр запрещён');
SELECT *
FROM "age_rating";

TRUNCATE TABLE "genre";
INSERT INTO "genre" ("genre")
VALUES ('Боевик'),
       ('Документальный'),
       ('Драма'),
       ('Комедия'),
       ('Мультфильм'),
       ('Триллер');
SELECT *
FROM "genre";

INSERT INTO "film" ("film_name", "description", "release_date", "duration", "age_rating")
VALUES ('Хищник', 'Динамичное кино', '1985-01-01', 100, SELECT ar."id"
                                                        FROM "age_rating" ar
                                                        WHERE ar."age_rating" = 'PG-13'),
       ('Появляется дракон', 'Драки', '1986-05-15', 100, SELECT ar."id"
                                                         FROM "age_rating" ar
                                                         WHERE ar."age_rating" = 'PG-13'),
       ('Унесённые ветром', 'Мелодрама', '1980-01-01', 200, SELECT ar."id"
                                                            FROM "age_rating" ar
                                                            WHERE ar."age_rating" = 'PG');
SELECT *
FROM "film";


