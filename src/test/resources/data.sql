insert into person (ID, Opis, Imię, Nazwisko, Nick, Email) values (1, 'a', 'Rafał', 'Szatkowski', 'Zenek', 'jakis@email.com');
insert into person (ID, Opis, Imię, Nazwisko, Nick, Email) values (2, 'User', 'User', 'User', 'User', 'user@email.com');
insert into person (ID, Opis, Imię, Nazwisko, Nick, Email) values (3, 'Admin', 'Admin', 'Admin', 'Admin', 'admin@email.com');
insert into person (ID, Opis, Imię, Nazwisko, Nick, Email) values (4, 'God', 'God', 'God', 'God', 'god@email.com');

insert into user_data (ID, Aktywny, ADMIN, GOD, USER, Login, ID_użytkownika, Hasło) values (5, 1, 0, 0, 1, 'user', 2, '$2a$10$mklO1pFVM8IRf/toPE5yUezYqDa32yFLA3DMmkTEyVA3VnceLDI4u');
insert into user_data (ID, Aktywny, ADMIN, GOD, USER, Login, ID_użytkownika, Hasło) values (6, 1, 1, 0, 1, 'admin', 3, '$2a$10$hZf2dAekk5CJYARmdrXyau1vFHMeAHdviETNuCY.SfxZ24il.D27q');
insert into user_data (ID, Aktywny, ADMIN, GOD, USER, Login, ID_użytkownika, Hasło) values (7, 1, 1, 1, 1, 'god', 4, '$2a$10$/o5K6qjKXowrDFbLKqJ3EOoSp5Pv7egeAqfWYCYA6gTgnEa7/Jr8S');

UPDATE person SET ID_danych_logowania = 5 where ID = 2;
UPDATE person SET ID_danych_logowania = 6 where ID = 3;
UPDATE person SET ID_danych_logowania = 7 where ID = 4;

insert into hibernate_sequence(next_val) values (8)