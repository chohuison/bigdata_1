package jpabook.start.service;

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


    }

}
