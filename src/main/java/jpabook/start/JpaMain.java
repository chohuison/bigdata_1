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
//            Hotel hotel = em.find(Hotel.class,2L);
//            ReservationStatus reservationStatus = new ReservationStatus();
//            Member member =em.find(Member.class,1L);
//            reservationStatus.setReview(false);
//            reservationStatus.setHotel(hotel);
//            reservationStatus.setCnt(2);
//            reservationStatus.setStartDay(LocalDate.of(2023,10,19));
//            reservationStatus.setFinalDay(LocalDate.of(2023,12,3));
//            reservationStatus.setTotalPrice(50000);
//            reservationStatus.setMember(member);
//            em.persist(reservationStatus);
            HotelDetailService hotelDetailService = new HotelDetailService();
            hotelDetailService.houseDetail(1L,11);

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
