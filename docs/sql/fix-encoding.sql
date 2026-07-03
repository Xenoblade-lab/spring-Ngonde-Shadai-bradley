-- Corrige les accents corrompus (?) sans effacer toute la base.
USE demo_lmd_2526_01;

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;

UPDATE professions SET description = 'Ingénieur civil' WHERE id = 5 OR description LIKE 'Ing%nieur%';
UPDATE formations SET description = 'Comptabilité générale' WHERE id = 3 OR description LIKE 'Comptabilit%';
UPDATE formations SET description = 'Développement Web' WHERE id = 4 OR description LIKE 'D%veloppement%';
UPDATE formations SET description = 'MySQL avancé' WHERE id = 2 OR description LIKE 'MySQL avanc%';

UPDATE candidats SET etat_civil = 'Célibataire' WHERE etat_civil LIKE 'C%libataire%';
UPDATE candidats SET etat_civil = 'Mariée' WHERE etat_civil LIKE 'Mari%e' AND genre = 'F';
UPDATE candidats SET etat_civil = 'Marié' WHERE etat_civil LIKE 'Mari%' AND genre = 'M';
