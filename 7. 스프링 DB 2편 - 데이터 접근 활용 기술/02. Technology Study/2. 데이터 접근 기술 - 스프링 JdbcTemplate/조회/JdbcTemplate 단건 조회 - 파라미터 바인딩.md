### 날짜 : 2023-03-27 16:31
### 주제 : 단건 조회 - 숫자 조회, 파라미터 바인딩
---
### 태그
* #조회, #단건_조회

### 메모
```java
int countOfActorsNamedJoe = jdbcTemplate.queryForObject( 
						"select count(*) from t_actor where first_name = ?",
						 Integer.class, 
						 "Joe");
```
* queryForObject를 통해 조회할 때 바인딩 변수 값을 Joe로 return 타입을 Integer.class로 지정
	* 이 예시와는 별개로 return Type을 String으로 할 경우 String.class로 하면 됨 

### 출처(참고문헌)
-  김영한 DB-2

### 연결문서
- [[JdbcTemplate 조회]]