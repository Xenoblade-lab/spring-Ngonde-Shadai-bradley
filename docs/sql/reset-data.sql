-- Réinitialise la base avec le jeu de données de démonstration.
-- Exécuter dans HeidiSQL ou : mysql -u root < docs/sql/reset-data.sql

USE demo_lmd_2526_01;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE candidats_formations;
TRUNCATE TABLE candidats;
TRUNCATE TABLE formations;
TRUNCATE TABLE professions;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO professions (description) VALUES
('Informaticien'),
('Comptable'),
('Infirmier'),
('Enseignant'),
('Ingénieur civil');

INSERT INTO formations (description, duree) VALUES
('Spring Framework', 3),
('MySQL avancé', 2),
('Comptabilité générale', 6),
('Développement Web', 4),
('Soins infirmiers', 12);

INSERT INTO candidats (profession_pk, noms, genre, etat_civil, lieu_nais, date_nais) VALUES
(1, 'Ngonde Shadai Bradley', 'M', 'Célibataire', 'Kinshasa', '2000-05-15'),
(2, 'Mbuyi Grace', 'F', 'Mariée', 'Lubumbashi', '1998-11-20'),
(3, 'Kabila Jean', 'M', 'Marié', 'Kinshasa', '1995-03-08'),
(4, 'Tshimanga Sarah', 'F', 'Célibataire', 'Mbuji-Mayi', '1999-07-22'),
(5, 'Mukendi Paul', 'M', 'Célibataire', 'Kananga', '2001-12-01');

INSERT INTO candidats_formations (candidat_pk, formation_pk) VALUES
(1, 1),
(1, 2),
(1, 4),
(2, 3),
(3, 4),
(4, 5),
(5, 1);
