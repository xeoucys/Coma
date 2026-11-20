USE coma_db;

INSERT INTO event (event_id, name, open_at, close_at, closed)
VALUES (1, '테스트 행사', '2026-11-20 00:00:00', '2026-12-31 23:59:59', FALSE);

-- remaining은 생성 시 capacity와 같게
INSERT INTO event_option (option_id, event_id, name, capacity, remaining)
VALUES (1, 1, '옵션A', 30, 30),
       (2, 1, '옵션B', 20, 20);

INSERT INTO event_pickup_date (event_id, pickup_date)
VALUES (1, '2026-12-01'),
       (1, '2026-12-02');

INSERT INTO member (student_id, name)
VALUES ('2312345', '홍길동'),
       ('2312346', '김철수'),
       ('2312347', '이영희');