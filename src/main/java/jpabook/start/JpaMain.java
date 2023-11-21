package jpabook.start;

import jpabook.start.domain.*;
import jpabook.start.service.HotelDetailService;

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


        try {
            tx.begin();
//            Member member = new Member();
//            member.setName("손초희");
//            member.setRoleType(RoleType.HOST);
//            em.persist(member);
//            HotelDetailService hotelDetailService = new HotelDetailService(em);
//            hotelDetailService.houseDetail(2L,11);
            Part569.pushStartData();
            //5번
            LocalDate checkinDate = LocalDate.now();
            LocalDate checkoutDate = LocalDate.now().plusDays(3);
            Part569.bookHouse(29L,34L,checkinDate,checkoutDate,"INDIVIDUAL",5);
            Part569.bookHouse(29L,34L,checkinDate,checkoutDate,"INDIVIDUAL",3);
            //6번
            Part569.cancelReserve(36L);
            tx.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage()); // 에러 메시지 출력
            e.printStackTrace(); // 에러 스택 트레이스 출력
            tx.rollback();
        } finally {
            em.close();
        }

    }
}
