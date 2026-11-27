USE coma_db;

DELETE FROM application;
UPDATE event_option SET remaining = capacity;
ALTER TABLE application AUTO_INCREMENT = 1;