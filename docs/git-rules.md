# Git 협업 규칙

Coma · 변연경, 서유정, 오지우

## 1. 브랜치 규칙

- **`main`에는 직접 작업하지 않는다.** `main`은 항상 실행되는 코드만 있는 브랜치이다.
- 기능 하나를 만들 때마다 `main`에서 새 브랜치를 만들어 작업한다.
- 작업이 끝나면 Pull Request(PR)를 올려 `main`에 합친다.

### 브랜치 이름: `종류/작업내용`

| 종류   | 언제      | 예시                                |
|--------|-----------|-------------------------------------|
| `feat` | 새 기능   | `feat/apply-api`, `feat/event-page` |
| `fix`  | 버그 수정 | `fix/stock-count`                   |
| `docs` | 문서      | `docs/api-spec`                     |

- 영어 소문자와 `-`만 사용한다.
- 브랜치 하나에는 기능 하나만 작업한다.

## 2. 커밋 메시지 규칙

```
종류: 무엇을 했는지
```

| 종류       | 의미                    | 예시                                   |
|------------|-------------------------|----------------------------------------|
| `feat`     | 기능 추가               | feat: 신청 API 추가                    |
| `fix`      | 버그 수정               | fix: 남은 수량이 음수가 되는 문제 수정 |
| `docs`     | 문서 수정               | docs: README 실행 방법 추가            |
| `refactor` | 동작은 같고 코드만 정리 | refactor: ApplyService 메서드 분리     |
| `test`     | 테스트 코드             | test: 동시 신청 테스트 추가            |
| `chore`    | 설정, 기타              | chore: .gitignore 수정                 |

- 메시지는 한글로 짧게 쓴다.
- 커밋은 작은 단위로 자주 한다. 하루 작업을 커밋 하나에 몰아넣지 않는다.
- 실행이 안 되는 코드는 커밋하지 않는다. (최소한 컴파일 오류는 없게)

## 3. 작업 순서

### ① 작업 시작 전: 최신 코드 받기

```bash
git checkout main
git pull origin main
```

### ② 브랜치 만들기

```bash
git checkout -b feat/apply-api
```

### ③ 작업하고 커밋하기

```bash
git add .
git commit -m "feat: 신청 API 추가"
```

### ④ GitHub에 올리기

```bash
git push origin feat/apply-api
```

### ⑤ GitHub에서 Pull Request 만들기

- 제목: 커밋 메시지와 같은 형식 (예: `feat: 신청 API 추가`)
- 내용: 무엇을 했는지, 어떻게 확인했는지 1~3줄

### ⑥ 팀원 1명 이상 확인 후 Merge

- 다른 팀원이 코드를 보고 문제없으면 Approve 후 Merge한다.
- 자기 PR은 자기가 Merge하지 않는다. (급할 때는 단톡방에 공유 후 Merge)

### ⑦ Merge 후 정리

```bash
git checkout main
git pull origin main
git branch -d feat/apply-api
```

## 4. 충돌을 줄이는 규칙

- **자기 담당 폴더 위주로 작업한다.**

| 담당 | 폴더 |
|---|---|
| 변연경 | `dao/`, `schema.sql`, `data.sql` |
| 서유정 | `static/` |
| 오지우 | `config/`, `controller/`, `service/`, `websocket/`, `exception/`, `test/` |

- **공통 파일(`dto/`, `application.properties`, `build.gradle`)을 고칠 때는 단톡방에 먼저 말한다.**
- 브랜치를 오래 두지 않는다. 기능이 작게라도 완성되면 바로 PR을 올린다. (2~3일 이내)
- 충돌이 나면 혼자 해결하지 말고, 해당 파일을 만든 사람과 같이 확인한다.

## 5. 하지 말 것

- `main`에 직접 push하기
- `git push --force` 사용하기
- DB 비밀번호가 들어 있는 `application-local.properties` 올리기
- `build/`, `.settings/`, `bin/` 같은 자동 생성 폴더 올리기 (`.gitignore`로 막혀 있는지 확인)

## 6. 처음 한 번 해둘 설정

- 저장소는 한 명이 만들고, 나머지 두 명을 **Settings → Collaborators**에서 초대한다.
- **Settings → Branches**에서 `main`에 보호 규칙을 걸고 "Require a pull request before merging"을 켠다. 실수로 `main`에 직접 push하는 것을 막아 준다.

