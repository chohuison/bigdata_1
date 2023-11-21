package jpabook.start.service;

import jpabook.start.domain.ReservationStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Part4 {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void addComments(Long guestId,Long reserveId,int starPoint, String comment){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            if (reserveId == null) {
                System.out.println("reservation not found");
                return;
            }

            // 숙소 생성
            ReservationStatus reservate = new ReservationStatus();


            em.persist(reservate);

            em.flush();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();

        }


    }

}
