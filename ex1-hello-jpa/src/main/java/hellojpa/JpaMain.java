package hellojpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx=em.getTransaction();
        tx.begin();

        try{
            // 생성
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

            // 수정
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = "+findMember.getId());
//            System.out.println("findMember.name = "+findMember.getName());
//            findMember.setName("HelloJPA");
//            System.out.println("findMember.name = "+findMember.getName());

            // JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();

            for (Member member :
                result) {
                System.out.println("member.name = "+member.getName());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
