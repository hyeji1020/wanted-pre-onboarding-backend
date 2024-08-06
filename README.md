# wanted-pre-onboarding-backend
원티드 백엔드 인턴십 지원 과제입니다.

### ✔사용 기술
Java(1.8), Spring Boot 2.7.1, Spring Data JPA, MySQL(8.0), h2

### ✔ERD
![image](https://github.com/user-attachments/assets/c6004dc3-8a23-4dfd-a6a6-a909fb7c9167)

### ✔API 명세서
https://documenter.getpostman.com/view/24847016/2sA3rxpsfn

<br>

## ✔구현 기능

### 1️⃣ 채용공고 등록/수정/삭제/조회
- 상세 조회 시 해당 회사가 올린 다른 채용공고 포함, 채용 내용 포함시켰습니다.

### 2️⃣ 사용자는 채용공고에 지원
- 사용자는 1회만 지원 가능,
일대일 매핑관계로 진행하여 1회만 지원하게 하였습니다.

### 3️⃣ 예외처리 및 예외 핸들링
- 도메인별로 세분화된 에러 코드와 예외 클래스를 설계하여, 명확한 예외 처리를 구현

### 4️⃣ 유효성 검사
- @Valid를 활용하여 입력 데이터에 대한 자동 검증을 수행하였습니다.

### 5️⃣ DTO 생성
- Request DTO를 사용함으로써 사용자 입력에 대한 유효성 검사 수행하였습니다.
- Response DTO를 사용하여 필요한 데이터만 전송하고, 엔티티의 민감한 정보가 외부로 노출되는 것을 방지하였습니다.

<br>

### ✔Unit Test
JobPost, Application Service 계층에 대해 테스트 코드를 작성하였습니다.
<br>
데이터의 변경이 운영 데이터에 영향을 주지 않도록 테스트 전용 데이터베이스(h2)를 사용하였습니다.

<br>

### ✔Commit Message Convention
| convention | 설명 |
| ------------ | ------------- |
| feature/feat | 새로운 기능 구현 |
| refactor | 서비스 로직이나 기능 수정  |
| fix | 오류 수정 |
| Docs | 	READ.ME 업데이트  |
