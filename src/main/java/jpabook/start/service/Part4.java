package jpabook.start.service;

import jpabook.start.domain.Hotel;
import jpabook.start.domain.Member;
import jpabook.start.domain.ReservationStatus;
import jpabook.start.domain.Review;

import javax.persistence.*;
import java.util.List;

public class Part4 {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    //리뷰 둥록
    //Part4.addComments(3L, 2L, 2L, 5,"사장님이 맛있고 음식이 친절해요" );
    public static void addComments(Long guestId, Long reserveId, Long hotelId ,Integer starPoint, String comment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            if (reserveId == null) {
                System.out.println("Reservation not found");
                return;
            }

            // 예약 상태 조회
            ReservationStatus reservationStatus = em.find(ReservationStatus.class, reserveId);
            if (reservationStatus == null) {
                System.out.println("Reservation not found");
                return;
            }

            // 이미 후기가 등록된 예약인지 확인
            if (reservationStatus.isReview()) {
                System.out.println("리뷰는 한 건만 작성 가능합니다.");
                return;
            }

            // 리뷰 생성
            Review review = new Review();
            review.setMember(em.find(Member.class, guestId));
            review.setReservationStatus(reservationStatus);
            review.setHotel(em.find(Hotel.class,hotelId));
            review.setStar(starPoint);
            review.setReview(comment);
            em.persist(review);

            // 예약 상태의 리뷰 여부 업데이트
            reservationStatus.setReview(true);

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
    // 게스트 마이페이지
    //Part4.ReservationHistory(3L, "terminated");
    public static void ReservationHistory(Long guestId, String findType) {
        EntityManager em = emf.createEntityManager();

        try {
            String queryStr = "SELECT r FROM ReservationStatus r WHERE r.member.id = :guestId";

            if ("oncoming".equals(findType)) {
                queryStr += " AND r.startDay >= CURRENT_DATE ORDER BY r.startDay DESC";
                System.out.println("[체크인 예정인 숙소 리스트]");
            } else if ("terminated".equals(findType)) {
                queryStr += " AND r.finalDay < CURRENT_DATE ORDER BY r.startDay DESC";
                System.out.println("[체크아웃이 완료된 숙소 리스트]");
            } else {
                queryStr += " ORDER BY r.startDay DESC";
                System.out.println("[전체 숙소 리스트]");
            }

            TypedQuery<ReservationStatus> query = em.createQuery(queryStr, ReservationStatus.class);
            query.setParameter("guestId", guestId);

            List<ReservationStatus> reservations = query.getResultList();

            System.out.printf("%-4s %-15s %-15s %-10s %-5s %s%n", "번호", "숙소명", "체크인", "체크아웃", "가격", "후기");

            for (int i = 0; i < reservations.size(); i++) {
                ReservationStatus reservation = reservations.get(i);
                String reviewStatus = reservation.isReview() ? "O" : "X";
                Integer totalPrice = reservation.getTotalPrice();
                String price = (totalPrice != null) ? totalPrice.toString() : "없음";

                String output = String.format("%-4d %-15s %-15s %-15s %-10s %s%n",
                        i + 1,
                        reservation.getHotel().getName(),
                        reservation.getStartDay(),
                        reservation.getFinalDay(),
                        price,
                        reviewStatus);

                System.out.print(output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}