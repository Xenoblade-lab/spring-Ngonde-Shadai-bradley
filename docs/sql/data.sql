USE demo_lmd_2526_01;

INSERT INTO professions (description) VALUES
('Informaticien'),
('Comptable'),
('Infirmier')
ON DUPLICATE KEY UPDATE description = VALUES(description);

INSERT INTO formations (description, duree) VALUES
('Spring Framework', 3),
('MySQL avancé', 2),
('Comptabilité générale', 6)
ON DUPLICATE KEY UPDATE duree = VALUES(duree);

INSERT INTO candidats (profession_pk, noms, genre, etat_civil, lieu_nais, date_nais) VALUES
(1, 'Ngonde Shadai Bradley', 'M', 'Célibataire', 'Kinshasa', '2000-05-15'),
(2, 'Mbuyi Grace', 'F', 'Mariée', 'Lubumbashi', '1998-11-20');

INSERT INTO candidats_formations (candidat_pk, formation_pk) VALUES
(1, 1),
(1, 2),
(2, 3);
