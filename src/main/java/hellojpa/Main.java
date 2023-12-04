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
            
            // 생성
            // Member member = new Member();
            // member.setId(2L);
            // member.setName("HelloB");
            // em.persist(member);

            // 조회
            // Member findMember = em.find(Member.class, 1L);
            // System.out.println("findMemeber.id = " + findMember.getId());
            // System.out.println("findMemeber.name = " + findMember.getName());

            // 삭제
            // Member findMember = em.find(Member.class, 1L);
            // em.remove(findMember);

            // 수정
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}