CREATE TABLE "films" (
  "film_id" integer PRIMARY KEY,
  "film_name" varchar,
  "description" varchar,
  "release_date" timestamp,
  "duration" integer,
  "age_rating" integer,
  "genres" integer
);

CREATE TABLE "users" (
  "user_id" integer PRIMARY KEY,
  "user_name" varchar,
  "user_login" varchar,
  "user_email" varchar,
  "user_birthday" timestamp
);

CREATE TABLE "likes" (
  "id" integer PRIMARY KEY,
  "film_id" integer,
  "user_id" integer
);

CREATE TABLE "friendships" (
  "id" integer PRIMARY KEY,
  "user_id" integer,
  "friend" integer,
  "is_approve" bool
);

CREATE TABLE "age_rating" (
  "id" integer PRIMARY KEY,
  "age_rating" varchar
);

CREATE TABLE "genre" (
  "id" integer PRIMARY KEY,
  "genre" varchar
);

CREATE TABLE "genre_links" (
  "id" integer PRIMARY KEY,
  "genre_id" integer,
  "film_id" integer
);

ALTER TABLE "genre_links" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "friendships" ADD FOREIGN KEY ("friend") REFERENCES "users" ("user_id");

ALTER TABLE "friendships" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "films" ADD FOREIGN KEY ("age_rating") REFERENCES "age_rating" ("id");

ALTER TABLE "genre_links" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");
