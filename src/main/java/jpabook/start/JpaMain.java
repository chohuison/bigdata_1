package jpabook.start;

import jpabook.start.domain.*;
import jpabook.start.service.HotelDetailService;
import jpabook.start.service.Part1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JpaMain {
     static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


        try {
            tx.begin();
//            Member member =new Member();
//            member.setName("손초희");
//            member.setRoleType(RoleType.HOST);
//             em.persist(member);
////             숙소 등록
            Address address = new Address();
                        address.setCity("경주시");
                        address.setStreet("한옥마을");
                        address.setZipCode("3412");

//    // 편의시설 생성
//            Convenience convenience = new Convenience();
//            convenience.setConvenienceType(ConvenienceType.DEFAULT);
//            convenience.setName("화장지");
//            em.persist(convenience);
//    Convenience convenience1 = em.find(Convenience.class, 1L);
//    List<Convenience> convenienceList =new ArrayList<>();
//                        convenienceList.add(convenience1);
//            Part1.registHouse(1L, "INDIVIDUAL", "숙소명", address, 1, 1,  1, "설명", convenienceList, 100000, 120000);
//            Member member = em.find(Member.class,1L);
            Hotel hotel = em.find(Hotel.class,3L);
//            ReservationStatus reservationStatus = new ReservationStatus();
//            reservationStatus.setMember(member);
//            reservationStatus.setReview(false);
//            reservationStatus.setHotel(hotel);
//            reservationStatus.setCnt(1);
//            reservationStatus.setStartDay(LocalDate.of(2023,11,24));
//            reservationStatus.setFinalDay(LocalDate.of(2023,11,26));
//            reservationStatus.setTotalPrice(300000);
//            em.persist(reservationStatus);
            HotelDetailService.houseDetail(3L,11);
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
