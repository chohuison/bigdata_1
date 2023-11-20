package jpabook.start;

import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class JpaMain {
     static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Part569.pushStartData();

        try {
            tx.begin();
//            HotelDetailService hotelDetailService = new HotelDetailService(em);
//            hotelDetailService.houseDetail(1L,11);



            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

    }
}
