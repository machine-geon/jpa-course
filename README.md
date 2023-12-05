# 03. 영속성 관리

## 영속성 컨텍스트(Persistence Context)

JPA에서 가장 중요한 2가지

- 객체와 관계형 데이터베이스 매핑하기
(Object Relational Mapping)
- 영속성 컨텍스트

### Persistence Context

- 영속성 컨텍스트: 엔티티를 영구 저장하는 환경
- 엔티티 매니저를 통해 영속성 컨텍스트에 접근
ex. EntityManager.persist(entity);

### 엔티티의 생명주기

- 비영속 (new/transient): 영속성 컨텍스트와 전혀 관계 없는 새로운 상태
- 영속 (managed): 영속성 컨텍스트에 관리되는 상태
- 준영속 (detached): 영속성 컨텍스트에 저장되었다가 분리된 상태
- 삭제 (removed): 삭제된 상태

persist를 호출 할 때에는 영속성 컨텍스트에 데이터를 담기만 하며,
실제 쿼리 실행은 Transaction을 commit 하는 순간에 쿼리가 실행된다.

### 영속성 컨텍스트의 이점

- 1차 캐시
- 동일성 보장 (identiyy)
- 트랜잭션을 지원하는 쓰기 지연 (transactional write-behind)
  - 커밋을 하는 순간 SQL문을 DB에 보낸다.
- 변경 감지 (Dirty checking)
    1. flush()
    2. 엔티티와 스냅샷 비교
    3. UPDATE SQL생성
    4. flush (영속성 컨텍스트의 변경내용을 DB에 반영)
    5. commit
- 지연 로딩 (lazy loading)

---

## 플러시 (Flush)

### 플러시 발생

- 변경 감지
- 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
- 쓰기 지연 SQL 저장소의 쿼리를 DB에 전송

### 플러시 하는 방법

- em.flush() - 직접 호출
- transaction commit - 플러시 자동 호출
- JPQL 쿼리 실행 - 플러시 자동 호출
  - exception 방지

### 플러시 모드 옵션

em.setFlushMode(FlushModeType.COMMIT)

- FlushModeType.AUTO: 커밋이나 쿼리를 실행할 때 플러시(default)
- FlushModeType.COMMIT: 커밋할때만 플러시

커밋 직접에만 동기화 하면 된다.

**플러시는 영속성 컨텍스트를 비우는 것이 아닌 영속성 컨텍스트의 변경 내용을 DB에 동기화.**

---

## 준영속 상태 (Detached)

### 준영속 상태

- 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)된 경우
- 영속성 컨텍스트가 제공하는 기능을 사용 못함.
ex. dirty checking, ...

준영속 상태로 만드는 방법

1. em.detach(entity)특정 엔티티만 준영속 상태로 전환
2. em.clear()영속성 컨텍스트를 완전히 초기화
3. em.close()영속성 컨텍스트를 종료
