### 날짜 : 2023-03-27 16:28
### 주제 : 단건 조회
---
### 태그
* #JdbcTemplate , #조회, #단건_조회

### 메모
```java
int rowCount = jdbcTemplate.queryForObject("select count(*) from t_actor", Integer.class);
```
* 하나의 로우를 조회함으로 queryForObject를 사용하고 반환받는 타입은 2번째 인자값으로 Integer.class라고 지정함

### 출처(참고문헌)
-  김영한 DB-2

### 연결문서
- [[JdbcTemplate 조회]]