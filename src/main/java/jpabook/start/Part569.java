package jpabook.start;

import jpabook.start.domain.Member;
import jpabook.start.domain.RoleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static jpabook.start.JpaMain.emf;

public class Part569
{
    public static void pushStartData()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Member g1 = new Member("안", RoleType.GUEST);
            Member g2 = new Member("소", RoleType.GUEST);
            Member g3 = new Member("정", RoleType.GUEST);
            Member g4 = new Member("안소정", RoleType.GUEST);
            g1.setId(1L);
            g2.setId(2L);
            g3.setId(3L);
            g4.setId(4L);
            Member h1 = new Member("김", RoleType.HOST);
            Member h2 = new Member("성", RoleType.HOST);
            Member h3 = new Member("렬", RoleType.HOST);
            Member h4 = new Member("김성렬", RoleType.HOST);
            h1.setId(5L);
            h2.setId(6L);
            h3.setId(7L);
            h4.setId(8L);
            em.persist(g1);
            em.persist(g2);
            em.persist(g3);
            em.persist(g4);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.persist(h4);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        System.out.println("=======기본 데이터 등록 완료=======");

    }
}
