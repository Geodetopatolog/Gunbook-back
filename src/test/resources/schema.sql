CREATE TABLE "club" (
                        "ID" bigint NOT NULL,
                        "Kursy" bit(1) NOT NULL,
                        "Opis" varchar(255) DEFAULT NULL,
                        "Rekreacja" bit(1) NOT NULL,
                        "URL_loga_klubu" varchar(255) DEFAULT NULL,
                        "Nazwa" varchar(255) DEFAULT NULL,
                        "Sport" bit(1) NOT NULL,
                        "Email" varchar(255) DEFAULT NULL,
                        "Ilość_członków" int NOT NULL,
                        PRIMARY KEY ("ID")
);

CREATE TABLE "shooting_range" (
                                  "ID" bigint NOT NULL,
                                  "Adres" varchar(255) DEFAULT NULL,
                                  "Opis" varchar(255) DEFAULT NULL,
                                  "Nazwa" varchar(255) DEFAULT NULL,
                                  PRIMARY KEY ("ID")
);

CREATE TABLE "event" (
                         "ID" bigint NOT NULL,
                         "Data_zakończenia" date DEFAULT NULL,
                         "Data_rozpoczęcia" date DEFAULT NULL,
                         "Opis" varchar(255) DEFAULT NULL,
                         "Wpisowe" int NOT NULL,
                         "Godzina_zakończenia" time DEFAULT NULL,
                         "Godzina_rozpoczęcia" time DEFAULT NULL,
                         "Tylko_członkowie" bit(1) NOT NULL,
                         "Nazwa" varchar(255) DEFAULT NULL,
                         "Zapisy" bit(1) NOT NULL,
                         "ID_strzelnicy" bigint DEFAULT NULL,
                         "Ilość_uczestników" int NOT NULL,
                         "Nazwa_strzelnicy" varchar(255) DEFAULT NULL,
                         "Zawody" bit(1) NOT NULL,
                         "Kurs" bit(1) NOT NULL,
                         "Praktyka" bit(1) NOT NULL,
                         PRIMARY KEY ("ID")
--                          KEY "FKe4mswhdf4vtjwirv37s8nisll" ("ID_strzelnicy"),
--                          CONSTRAINT "FKe4mswhdf4vtjwirv37s8nisll" FOREIGN KEY ("ID_strzelnicy") REFERENCES "shooting_range" ("ID")
);

CREATE TABLE "user_data" (
                             "ID" bigint NOT NULL,
                             "Aktywny" bit(1) NOT NULL,
                             "ADMIN" bit(1) NOT NULL,
                             "GOD" bit(1) NOT NULL,
                             "USER" bit(1) NOT NULL,
                             "Login" varchar(255) DEFAULT NULL,
                             "ID_użytkownika" bigint DEFAULT NULL,
                             "Hasło" varchar(255) DEFAULT NULL,
                             PRIMARY KEY ("ID")
--                              KEY "FK15p4jlb1fwfnfge30b15khfyu" ("ID_użytkownika"),
--                              CONSTRAINT "FK15p4jlb1fwfnfge30b15khfyu" FOREIGN KEY ("ID_użytkownika") REFERENCES "person" ("ID") ON DELETE CASCADE
);

CREATE TABLE "person" (
                          "id" bigint NOT NULL,
                          "Opis" varchar(255) DEFAULT NULL,
                          "Imię" varchar(255) DEFAULT NULL,
                          "Nick" varchar(255) DEFAULT NULL,
                          "Nazwisko" varchar(255) DEFAULT NULL,
                          "Email" varchar(255) DEFAULT NULL,
                          "ID_danych_logowania" bigint DEFAULT NULL,
                          PRIMARY KEY ("ID")
--                           KEY "FKs3fskgqhbw2s1tjxkviwtomve" ("ID_danych_logowania"),
--                           CONSTRAINT "FKs3fskgqhbw2s1tjxkviwtomve" FOREIGN KEY ("ID_danych_logowania") REFERENCES "user_data" ("ID") ON DELETE CASCADE
);

CREATE TABLE "hibernate_sequence" (
    "next_val" bigint DEFAULT NULL
);

ALTER TABLE "event"
    ADD CONSTRAINT "FKe4mswhdf4vtjwirv37s8nisll" FOREIGN KEY ("ID_strzelnicy") REFERENCES "shooting_range" ("ID");

ALTER TABLE "user_data"
    ADD CONSTRAINT "FK15p4jlb1fwfnfge30b15khfyu" FOREIGN KEY ("ID_użytkownika") REFERENCES "person" ("ID") ON DELETE CASCADE;

ALTER TABLE "person"
    ADD CONSTRAINT "FKs3fskgqhbw2s1tjxkviwtomve" FOREIGN KEY ("ID_danych_logowania") REFERENCES "user_data" ("ID") ON DELETE CASCADE;

CREATE TABLE "clubs_applications" (
                                      "id_person" bigint NOT NULL,
                                      "id_club" bigint NOT NULL,
                                      KEY "FKleckhcbj280uwh4tcet3bte0p" ("id_club"),
                                      KEY "FK25f67cw3u87qho8rmof451ie2" ("id_person"),
                                      CONSTRAINT "FK25f67cw3u87qho8rmof451ie2" FOREIGN KEY ("id_person") REFERENCES "person" ("ID"),
                                      CONSTRAINT "FKleckhcbj280uwh4tcet3bte0p" FOREIGN KEY ("id_club") REFERENCES "club" ("ID")
);

CREATE TABLE "clubs_members" (
                                 "id_person" bigint NOT NULL,
                                 "id_club" bigint NOT NULL,
                                 KEY "FKrn8ljoa05919sprpluxkhaj0" ("id_club"),
                                 KEY "FKoxeypetp8b2m90rpmq4d4r8tj" ("id_person"),
                                 CONSTRAINT "FKoxeypetp8b2m90rpmq4d4r8tj" FOREIGN KEY ("id_person") REFERENCES "person" ("ID"),
                                 CONSTRAINT "FKrn8ljoa05919sprpluxkhaj0" FOREIGN KEY ("id_club") REFERENCES "club" ("ID")
);

CREATE TABLE "clubs_owners" (
                                "id_person" bigint NOT NULL,
                                "id_club" bigint NOT NULL,
                                KEY "FK6h68v8wn983xwc6iw588jy2v6" ("id_club"),
                                KEY "FKha4c3okbaxpl449a3he4mrp74" ("id_person"),
                                CONSTRAINT "FK6h68v8wn983xwc6iw588jy2v6" FOREIGN KEY ("id_club") REFERENCES "club" ("ID"),
                                CONSTRAINT "FKha4c3okbaxpl449a3he4mrp74" FOREIGN KEY ("id_person") REFERENCES "person" ("ID")
);

CREATE TABLE "clubs_ranges" (
                                "ID_strzelnicy" bigint NOT NULL,
                                "ID_klubu" bigint NOT NULL,
                                KEY "FK9526xkwear8kdlgpk2eq8gwb4" ("ID_strzelnicy"),
                                KEY "FKaqx8xnr48evvmeonxwcrmn2mj" ("ID_klubu"),
                                CONSTRAINT "FK9526xkwear8kdlgpk2eq8gwb4" FOREIGN KEY ("ID_strzelnicy") REFERENCES "shooting_range" ("ID"),
                                CONSTRAINT "FKaqx8xnr48evvmeonxwcrmn2mj" FOREIGN KEY ("ID_klubu") REFERENCES "club" ("ID")
);

CREATE TABLE "event_organizers" (
                                    "id_event" bigint NOT NULL,
                                    "id_club" bigint NOT NULL,
                                    KEY "FK4ubq20ol7ute8mlmpsb2t43sf" ("id_club"),
                                    KEY "FK2oyr5sbgyu80mu5r9vnjy92kl" ("id_event"),
                                    CONSTRAINT "FK2oyr5sbgyu80mu5r9vnjy92kl" FOREIGN KEY ("id_event") REFERENCES "event" ("ID"),
                                    CONSTRAINT "FK4ubq20ol7ute8mlmpsb2t43sf" FOREIGN KEY ("id_club") REFERENCES "club" ("ID")
);

CREATE TABLE "event_participants" (
                                      "id_event" bigint NOT NULL,
                                      "id_person" bigint NOT NULL,
                                      KEY "FKpos55q8c1ftrjw2ro2mmkd2he" ("id_person"),
                                      KEY "FKfm7cph4cvtfokemxxv5vwco4r" ("id_event"),
                                      CONSTRAINT "FKfm7cph4cvtfokemxxv5vwco4r" FOREIGN KEY ("id_event") REFERENCES "event" ("ID"),
                                      CONSTRAINT "FKpos55q8c1ftrjw2ro2mmkd2he" FOREIGN KEY ("id_person") REFERENCES "person" ("ID")
);

CREATE TABLE "event_participants_requests" (
                                               "id_event" bigint NOT NULL,
                                               "id_person" bigint NOT NULL,
                                               KEY "FKi3qrqvod7wrvuhgjvmgbdxijc" ("id_person"),
                                               KEY "FKm6an02ps8f8t1wljqt8bx5t00" ("id_event"),
                                               CONSTRAINT "FKi3qrqvod7wrvuhgjvmgbdxijc" FOREIGN KEY ("id_person") REFERENCES "person" ("ID"),
                                               CONSTRAINT "FKm6an02ps8f8t1wljqt8bx5t00" FOREIGN KEY ("id_event") REFERENCES "event" ("ID")
);
