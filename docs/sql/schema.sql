CREATE DATABASE IF NOT EXISTS demo_lmd_2526_01
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE demo_lmd_2526_01;

CREATE TABLE IF NOT EXISTS professions (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(45) NOT NULL,
    UNIQUE KEY uk_professions_description (description)
);

CREATE TABLE IF NOT EXISTS formations (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(45) NOT NULL,
    duree       INT NOT NULL,
    UNIQUE KEY uk_formations_description (description)
);

CREATE TABLE IF NOT EXISTS candidats (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    profession_pk BIGINT NOT NULL,
    noms          VARCHAR(100) NOT NULL,
    genre         CHAR(1) NOT NULL,
    etat_civil    VARCHAR(20) NOT NULL,
    lieu_nais     VARCHAR(45) NOT NULL,
    date_nais     DATE NOT NULL,
    CONSTRAINT fk_candidats_profession
        FOREIGN KEY (profession_pk) REFERENCES professions(id)
);

CREATE TABLE IF NOT EXISTS candidats_formations (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidat_pk  BIGINT NOT NULL,
    formation_pk BIGINT NOT NULL,
    CONSTRAINT fk_cf_candidat
        FOREIGN KEY (candidat_pk) REFERENCES candidats(id),
    CONSTRAINT fk_cf_formation
        FOREIGN KEY (formation_pk) REFERENCES formations(id),
    UNIQUE KEY uk_candidat_formation (candidat_pk, formation_pk)
);

CREATE INDEX idx_candidats_profession ON candidats(profession_pk);
CREATE INDEX idx_cf_candidat ON candidats_formations(candidat_pk);
CREATE INDEX idx_cf_formation ON candidats_formations(formation_pk);
