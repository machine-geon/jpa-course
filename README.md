# ****객체와 테이블 매핑 (mapping)****

## **엔티티 매핑**

- 객체와 테이블 매핑 : @Entity, @Table
- 필드와 컬럼 매핑 : @Column
- 기본 키 매핑 : @Id
- 연관관계 매핑 : @ManyToOne, @JoinColumn

### **@Entity**

- JPA를 사용해서 테이블과 매핑할 클래스는 필수
- 기본 생성자 필수
- final 클래스, enum, interface, inner 클래스 사용 X
- 저장할 필드에 final 사용 X

**@Entity 속성 정리**

name

- JPA에서 사용할 엔티티 이름을 지정
- 기본값 : 클래스 이름을 그대로 사용
- 같은 클래스 이름이 없으면 가급적 기본값을 사용한다.

### @Table

| name | 매핑할 table name |
| --- | --- |
| catalog | DB catalog mapping |
| shcema | DB schema mapping |
| uniqueConstraints(DDL) | DDL 생성 시에 유니크 제약조건 생성 |

---

# ****데이터베이스 스키마 자동 생성 (DB Schema auto create)****

## **데이터베이스 스키마 자동 생성**

- DDL을 애플리케이션 실행 시점에 자동 생성
- 테이블 중심 -> 객체 중심
- 데이터베이스 방언(dialect)을 활용해서 데이터베이스에 맞는 적절한 DDL 생성
- 이렇게 **생성된 DDL은 개발 장비에서만 사용**
- 생성된 DDL은 운영서버에서는 사용하지 않거나, 적절히 다듬은 후 사용

**데이터 베이스 스키마 자동생성 - 속성**

hibernate.hbm2ddl.auto

| create | 기존 테이블 삭제 후 다시 생성(DROP + CREATE) |
| --- | --- |
| create-drop | create와 같으나 종료시점에 테이블 drop |
| update | 변경분만 반영(운영DB에는 사용 X) |
| validate | 엔티티와 테이블이 정상 매핑되었는지만 확인 |
| none | 사용하지 않음 |

**데이터베이스 스키마 자동생성 주의**

운영 장비에는 절대 create, create-drop, update 사용 X

**DDL 생성 기능**

- 제약 조건 추가 : 회원 이름은 필수, 10자 초가X
    - @Column(nullable = false, length = 10)
- 유니크 제약조건 추가
    - @Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNaes = {"NAME", "AGE"})})
- DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향 X

---

# ****필드와 컬럼 매핑 (feild, column mapping)****

## 매핑 어노테이션

| @Column | 컬럼 매핑 |
| --- | --- |
| @Temporal | 날짜 타입 매핑 |
| @Enumerated | enum 타입 매핑 |
| @Lob | BLOB,CLOB 매핑 |
| @Transient | 특정 필드를 컬럼에 매핑을 하지 않고, 메모리에서만 사용 |

### **@Column**

| Attribute | Description | Default |
| --- | --- | --- |
| name | 필드와 매핑할 테이블의 컬럼 이름 | 객체의 필드 이름 |
| insertable, updatable | 등록, 변경 가능 여부 | TRUE |
| nullable(DDL) | null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시에 not null 제약조건이 붙는다. | TRUE(null )FALSE(not null) |
| unique(DDL) | @Table의 uniqueconstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다.(@table의 uniqueConstraints를 통한 유니크 제약을 선호) |  |
| columnDefinition(DDL) | 데이터베이스 컬럼 정보를 직접 줄 수 있다.ex. varchar(100) default 'EMPTY' | 필드의 자바 탑이과 방언 정보를 직접 기입 |
| length(DDL) | 문자 길이 제약조건, String 타입에서만 사용한다. | 255 |
| precision scale(DDL) | BigDecimal 타입에서 사용한다. (BigInteger도 사용 가능)precision은 소수점을 포함한 전체 자릿수를, scale은 소수의 자릿수다.참고로 double,float 타입에는 적용되지 않는다. 정밀한 소수를 다루어야 할때만 사용한다. | precision = 19 |

### **@Enumerated**

- EnumType.ORDINAL: enum 순서를 데이터베이스에 저장. (사용 X)
- EnumType.STRING: enum 이름를 데이터베이스에 저장.(필수 O)
- default : EnumType.ORDINAL

### **@Temporal**

- LocalDate, LocalDateTime을 사용할 때는 생략 가능. (java8 버전 이후)
- TemporalType.DATE : 날짜, 데이터베이스 data 타입과 매핑
- TemporalType.TIME : 시간, 데이터베이스 time 타입과 매핑
- TemporalType.TIMESTAMP : 날짜와 시간, 데이터베이스 timestamp 타입과 매핑

### **@Lob**

- 지정할 수 있는 속성이 없다.
- 매핑하는 필드 타입이 문자면 CLOB, 나머지는 BLOB 매핑

### **@Transient**

- 필드 매핑 X
- 데이터베이스에 저장X, 조회X
- 메모리상에서만 사용을 원할때 사용.

---

# ****기본키 매핑 (Primary key Mapping)****

## 기본키 매핑 (Primary key Mapping)

기본 키 매핑 어노테이션

직접 할당 : @Id 만 사용

자동 생성 : @GeneratedValue(strategy = GenerationType.'전략')

**IDENTITY : 데이터베이스에 위임**

ex. MYSQL - AUTO_INCREMENT

<aside>
💡 JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL을 실행하지만

AUTO_INCREMENT는 DB에 INSERT SQL을 실행해야 ID를 알 수 있기 때문에

em.persist() 시점에 즉시 INSERT SQL을 실행 하고 DB에서 식별자를 조회하게 된다.

</aside>

```java
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	...
}
```

**SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용**

SequenceGenerator 필요

<aside>
💡 영속성 컨텍스트에 저장되기 전 DB에 접근해 SEQUENCE를 얻어온다.

네트워크를 많이 타는 이슈를 해결하기위한(최적화) 방법

→ allocationSize 옵션을 통해 DB에서 allocationSize 만큼 Sequence를 가져온다.

이후 메모리에 올려놓은 채로 사용하게 된다.

해당 개수에 달했을 경우 다시 allocationSize만큼의 SEQUENCE를 DB에서 가져온다.

주의. 웹서버를 내린경우 공백이 발생. ( 50 ~ 100 권장)

</aside>

```java
@Entity
@SequenceGenerator(
name = "MEMBER_SEQ_GENERATOR",
sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 시쿼스 이름
initialValue = 1, allocationSize = 1)
	public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	private Long id;
	...
}
```

**TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용**

장점 : 모든 DB에 적용 가능 / 단점 : 성능

@TableGenerator 필요

```java
@Entity
@TableGenerator(
name = "MEMBER_SEQ_GENERATOR",
table = "MY_SEQUENCES",
pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {
    @Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
	private Long id;
	...
}
```

**AUTO : 방언에 따라 자동 지정, 기본값**

**권장하는 식별자 전략**

- 기본 키 제약 조건 : Not Null, 유일, 불변
- 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키, ex. GenerateValue)를 사용하자.
- ex. 주민등록번호도 기본키로 적절하지 않다. (ex.정책변경으로 주민등록번호 보관 불가 상황)
- 권장 : Long형 + 대체키 + 키 생성전략 사용