# 테스트 시나리오 (DB 파트)

- 테스트 일자: 2026-11-27
- 테스트 환경: MariaDB 13.0.2, HeidiSQL 12.20.0.7320
- 대상 DB: `coma_db`
- 테스트 방식: 다른 파트(서비스/화면) 미완성으로, HeidiSQL에서 SQL을 직접 실행하여 DB 레벨 동작을 검증

## 시연용 데이터

| 항목 | 내용 |
|---|---|
| 행사 | 1개 (`event_id` = 1) |
| 옵션 1 | 정원(capacity) 30 |
| 옵션 2 | 정원(capacity) 20 |
| 테스트 신청자 | 학번 `2312345`, 이름 `홍길동`, 수령일 `2026-12-01` |
| 초기화 | `docs/sql/reset.sql` (신청 내역 삭제, `remaining = capacity`, AUTO_INCREMENT 1로 리셋) |

## 테스트 결과 요약

| 번호 | 시나리오 | 기대 결과 | 실제 결과 | 통과 |
|---|---|---|---|---|
| 1 | 정상 신청 | 신청 1건 등록, 옵션 1 남은 수량 1 감소 | `application` 0행 → 1행, 옵션 1 `remaining` 30 → 29, UPDATE `Rows matched: 1 / Changed: 1` | O |
| 2 | 중복 신청 | UNIQUE 제약 위반 에러, 신청 내역 증가 없음 | `SQL 오류 (1062): Duplicate entry '2312345-1' for key 'uk_application_student_event'`, `application` 1행 유지 | O |
| 3 | 마감 | 첫 차감은 성공, 남은 수량 0이면 차감 안 됨 | 옵션 2 `remaining` 1 → 0 (`Changed: 1`), 한 번 더 실행 시 `Rows matched: 0 / Changed: 0` | O |

## 시나리오별 상세

### 0. 시작 상태 (리셋 후)

`reset.sql` 실행 후 아래 두 쿼리로 확인.

```sql
SELECT option_id, capacity, remaining FROM event_option;
SELECT * FROM application;
```

| option_id | capacity | remaining |
|---|---|---|
| 1 | 30 | 30 |
| 2 | 20 | 20 |

- `application`: 0행

### 1. 정상 신청

```sql
INSERT INTO application (event_id, option_id, student_id, name, phone, pickup_date)
VALUES (1, 1, '2312345', '홍길동', '010-0000-0000', '2026-12-01');

UPDATE event_option SET remaining = remaining - 1 WHERE option_id = 1 AND remaining > 0;
```

- 로그: UPDATE `Rows matched: 1  Changed: 1`, 두 문장 실행 `영향 받은 행: 2` (INSERT 1 + UPDATE 1)

실행 후 `event_option`

| option_id | capacity | remaining |
|---|---|---|
| 1 | 30 | 29 |
| 2 | 20 | 20 |

실행 후 `application` (1행)

| application_id | event_id | option_id | student_id | name | phone | pickup_date | applied_at | paid |
|---|---|---|---|---|---|---|---|---|
| 1 | 1 | 1 | 2312345 | 홍길동 | 010-0000-0000 | 2026-12-01 | 2026-11-27 11:44:54 | 0 |

### 2. 중복 신청

시나리오 1의 INSERT를 동일한 값으로 다시 실행.

- 에러: `SQL 오류 (1062): Duplicate entry '2312345-1' for key 'uk_application_student_event'`
- 같은 학번 + 같은 행사 조합이 UNIQUE 제약(`uk_application_student_event`)으로 차단됨
- 실행 후 `SELECT * FROM application` 결과 1행 (시나리오 1에서 넣은 데이터 그대로, 추가 행 없음)

### 3. 마감

```sql
UPDATE event_option SET remaining = 1 WHERE option_id = 2;

UPDATE event_option SET remaining = remaining - 1 WHERE option_id = 2 AND remaining > 0;
-- 위 차감 UPDATE를 한 번 더 실행
```

| 실행 | 로그 | 결과 |
|---|---|---|
| `remaining = 1` 설정 | `Rows matched: 1  Changed: 1` | 옵션 2 remaining 1 |
| 차감 UPDATE 1회차 | `Rows matched: 1  Changed: 1` | remaining 1 → 0 |
| 차감 UPDATE 2회차 | `Rows matched: 0  Changed: 0` | 변화 없음 (마감) |

테스트 3 종료 후 `event_option`

| option_id | capacity | remaining |
|---|---|---|
| 1 | 30 | 29 |
| 2 | 20 | 0 |

`remaining > 0` 조건 덕분에 남은 수량이 0일 때 더 이상 차감되지 않음을 확인.

## 증거 캡처

`docs/test-images/` 폴더에 보관.

| 단계 | 내용 |
|---|---|
| 시작 상태 | `event_option` 30/30, 20/20, `application` 0행 |
| 테스트 1 | 로그(INSERT, UPDATE `Changed: 1`), `event_option`(옵션 1이 29), `application` 1행 |
| 테스트 2 | 1062 에러창, 하단 로그, `application` 1행 유지 |
| 테스트 3 | 하단 로그(`Changed: 1` → `Changed: 0`), `event_option`(옵션 2가 0) |

## 참고 사항

- 위 값은 모두 HeidiSQL에서 실제 실행한 결과 화면과 하단 로그 기준.
- 테스트 종료 후 `reset.sql`로 원상복구.

## 미완료 (다른 파트 완성 후)

- 화면/API를 통한 동일 시나리오 재테스트
- 정원만큼 실제 신청을 넣어서 마감되는 흐름 시연
- 동시 신청 처리 확인
