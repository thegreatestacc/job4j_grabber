create table post(
                     id serial primary key,
                     name varchar,
                     text varchar,
                     link varchar,
                     created date unique
);