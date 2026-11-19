# Coma

자프실2 간식 신청 프로그램 개발을 위한 레포지토리입니다.

학과 간식 사업의 선착순 신청을 받고, 정원 관리와 실시간 수량 갱신을 처리하는 웹 서비스입니다.

## 팀원

| 이름 | 역할 |
|---|---|
| 변연경 | DB (테이블 설계, DAO, SQL) |
| 서유정 | View (학생 웹 화면) |
| 오지우 | 메인로직 (Spring Boot 서버, WebSocket) |

## 기술 스택

- Java 17, Spring Boot 3.5
- HTML, CSS, JavaScript
- WebSocket
- MariaDB, JdbcTemplate
- Gradle

## 처음 실행하기

1. MariaDB에 DB를 만들고, `schema.sql`과 `data.sql`을 순서대로 실행한다. (HeidiSQL 등)
   ```sql
   CREATE DATABASE coma_db DEFAULT CHARACTER SET utf8mb4;
   ```
2. `src/main/resources/application-local.properties.example`을 복사해서 같은 폴더에 `application-local.properties`로 저장하고, 자기 MariaDB 아이디와 비밀번호를 적는다. (이 파일은 Git에 올라가지 않는다)
3. Eclipse에서 **File → Import → Gradle → Existing Gradle Project**로 이 폴더를 연다.
4. `SnackApplication.java`를 우클릭하고 **Run As → Spring Boot App** (또는 Java Application)으로 실행한다.
5. 브라우저에서 `http://localhost:8080` 에 접속한다.

Eclipse 설정: **Window → Preferences → General → Workspace → Text file encoding**을 **UTF-8**로 맞춘다.

## 폴더 구조 (1순위)

```
src/main/java/com/coma/snack/
├── SnackApplication.java
├── config/       WebSocketConfig                      (오지우)
├── controller/   EventController, ApplyController     (오지우)
├── service/      EventService, ApplyService           (오지우)
├── websocket/    StockWebSocketHandler, StockNotifier (오지우)
├── dao/          EventDao, OptionDao, ApplicationDao  (변연경)
├── dto/          요청·응답 DTO, EventStatus            (함께)
└── exception/    ErrorCode, ApplyException, GlobalExceptionHandler (오지우)

src/main/resources/
├── application.properties
├── application-local.properties.example
├── schema.sql, data.sql                                (변연경)
└── static/                                             (서유정)
    ├── index.html, event.html                          ← 1순위
    ├── my.html                                         ← 2순위
    ├── login.html, signin.html, applicants.html,
    │   event-management.html, members.html             ← 관리자 (2순위 이후)
    ├── css/style.css
    └── js/   api.js, socket.js, 페이지별 JS (페이지 이름과 같음)

src/test/java/com/coma/snack/service/ApplyServiceTest   (오지우)
```

각 파일의 TODO 주석에 만들어야 할 내용과 관련 설계 문서 위치가 적혀 있습니다. 화면 파일 이름은 UI 설계의 페이지 이름과 같습니다.

## 1순위 기능

행사 조회 → 간식 신청 → 신청 기간 확인 → 정원 차감·품절 → 중복 신청 방지 → 신청 결과 확인 → 동시 신청 처리 → 실시간 수량 갱신

## 문서

- [시스템 구조 설계](docs/design-system-structure.md)
- [서버 및 통신 설계 (API 명세)](docs/design-server-api.md)
- [Git 협업 규칙](docs/git-rules.md)
