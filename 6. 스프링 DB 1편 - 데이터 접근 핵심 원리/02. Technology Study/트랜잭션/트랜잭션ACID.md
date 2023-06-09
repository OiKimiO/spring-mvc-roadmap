### 날짜 : 2023-03-24 15:36
### 주제 : 트랜잭션ACID
---
### 태그
* #트랜잭션, #Atomicitiy, #Consistency, #Isolation, #Durability

### 메모
-   트랜잭션은 각각 **원자성**(Atomicitiy), **일관성**(Consistency), **격리성**(Isolation), **지속성**(Durability)을 보장

-   **원자성**(Atomicitiy)
    -   트랜잭션 내 작업은 하나의 작업인 것 처럼 모두 성공하거나 실패해야함
-   **일관성**(Consistency)
    -   모든 트랜잭션은 일관성 있는 데이터 베이스 상태를 유지
        -   데이터베이스에서 정한 무결성 제약 조건을 항상 만족해야 함
-   **격리성**(Isolation)
    -   동시에 실행되는 트랜잭션들이 서로에게 영향을 미치지 않도록 격리
        -   격리성은 동시성과 관련된 성능 이슈로 인해 트랜잭션 격리 수준(Isolation level)을 선택
-   **지속성**(Durability)
    -   트랜잭션을 성공적으로 끝내면 그 결과는 항상 기록
        -   중간에 시스템 문제가 발생해도 데이터베이스 로그 등을 사용해 트랜잭션 내용을 복구

### 출처(참고문헌)
-  김영한 DB-1

### 연결문서
- 