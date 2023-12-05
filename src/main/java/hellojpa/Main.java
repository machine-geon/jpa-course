package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            
            // // 비영속
            // Member member = new Member();
            // member.setId(101L);
            // member.setName("HelloJPA");

            // // 영속
            // System.out.println("=== Befor ===");
            // em.persist(member);
            // System.out.println("=== After ===");

            // // 1차 캐시 조회
            // Member findMember1 = em.find(Member.class, 101L);
            // Member findMember2 = em.find(Member.class, 101L);

            // // 영속 엔티티의 동일성 보장
            // System.out.println("result = " + (findMember1 == findMember2));

            // // 쓰기 지연
            // Member member1 = new Member(150L, "A");
            // Member member2 = new Member(160L, "B");
            
            // em.persist(member1);
            // em.persist(member2);
            // System.out.println("================");

            // // dirty checking
            // Member member = em.find(Member.class, 150L);
            // member.setName("ZZZZZ");
            // /** 수정에는 persist 사용 X
            // em.persist(member); */
            // System.out.println("================");

            // // flush
            // Member member = new Member(200L, "member200");
            // em.persist(member);

            // em.flush();

            // System.out.println("================");

            // // 준영속
            // Member member = em.find(Member.class, 150L);
            // member.setName("AAAAA");

            // em.detach(member);
            // em.clear();

            // Member member2 = em.find(Member.class, 150L);

            // System.out.println("================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}