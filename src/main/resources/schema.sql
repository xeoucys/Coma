USE coma_db;

DROP TABLE IF EXISTS application;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS event_option;
DROP TABLE IF EXISTS event_pickup_date;
DROP TABLE IF EXISTS event;

CREATE TABLE event (
  event_id INT NOT NULL AUTO_INCREMENT,
  name     VARCHAR(100) NOT NULL,
  open_at  DATETIME NOT NULL,
  close_at DATETIME NOT NULL,
  closed   BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (event_id)
);

CREATE TABLE event_pickup_date (
  event_id    INT NOT NULL,
  pickup_date DATE NOT NULL,
  PRIMARY KEY (event_id, pickup_date),
  FOREIGN KEY (event_id) REFERENCES event (event_id) ON DELETE CASCADE
);

CREATE TABLE event_option (
  option_id INT NOT NULL AUTO_INCREMENT,
  event_id  INT NOT NULL,
  name      VARCHAR(64) NOT NULL,
  capacity  INT NOT NULL,
  remaining INT NOT NULL,
  PRIMARY KEY (option_id),
  UNIQUE KEY uk_event_option_option_event (option_id, event_id),
  CHECK (remaining >= 0 AND remaining <= capacity),
  FOREIGN KEY (event_id) REFERENCES event (event_id) ON DELETE CASCADE
);

CREATE TABLE member (
  student_id CHAR(7) NOT NULL,
  name       VARCHAR(64) NOT NULL,
  PRIMARY KEY (student_id)
);

CREATE TABLE application (
  application_id INT NOT NULL AUTO_INCREMENT,
  event_id       INT NOT NULL,
  option_id      INT NOT NULL,
  student_id     CHAR(7) NOT NULL,
  name           VARCHAR(64) NOT NULL,
  phone          VARCHAR(20) NOT NULL,
  pickup_date    DATE NOT NULL,
  applied_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  paid           BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (application_id),
  UNIQUE KEY uk_application_student_event (student_id, event_id),
  FOREIGN KEY (event_id) REFERENCES event (event_id) ON DELETE CASCADE,
  FOREIGN KEY (option_id, event_id) REFERENCES event_option (option_id, event_id) ON DELETE CASCADE,
  FOREIGN KEY (student_id) REFERENCES member (student_id) ON DELETE CASCADE,
  FOREIGN KEY (event_id, pickup_date) REFERENCES event_pickup_date (event_id, pickup_date) ON DELETE CASCADE
);