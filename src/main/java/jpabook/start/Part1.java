package jpabook.start;

import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    // 1. 숙소 등록
    public static void registHouse() {
        //이름, 주소, 소유자(호스트), 수용 인원, 침실/침대/욕실 개수, 숙소 소개, 숙소 편의시설, 요금 정책(평일과 주말) 등록
        // 엔터티 매니저 생성
        EntityManager em = emf.createEntityManager();

        // 트랜잭션 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member host = em.find(Member.class, 5L);

            if (host == null) {
                System.out.println("Host not found!");
                return;
            }

            // 편의시설 생성
            Convenience convenience1 = new Convenience();
            convenience1.setName("Wi-Fi");
            convenience1.setConvenienceType(ConvenienceType.DEFAULT);
            em.persist(convenience1);

            Convenience convenience2 = new Convenience();
            convenience2.setName("Parking");
            convenience2.setConvenienceType(ConvenienceType.ACCESSIBILITY);
            em.persist(convenience2);

            // 할인 정책 생성
            Discount discountAmount = new Discount();
            discountAmount.setDiscountType(DiscountType.QUANTITY);
            discountAmount.setValue(5000);

            Discount discountRate = new Discount();
            discountRate.setDiscountType(DiscountType.RATE);
            discountRate.setValue(10);

            // 숙소 생성
            Hotel hotel = new Hotel();
            hotel.setName("호텔임");
            hotel.setMember(host);
            hotel.setContent("설명임");
            hotel.setPrice(new Price(100000, 120000)); // 주말과 평일 가격
            hotel.setBedCount(2);
            hotel.setToiletCount(1);
            hotel.setRoomType(RoomType.INDIVIDUAL);
            hotel.setRoomCount(3);

            // 할인 정책을 숙소에 연결
            List<Discount> discounts = new ArrayList<>();
            discounts.add(discountAmount);
            discounts.add(discountRate);
            hotel.setDiscount(discounts);

            // 편의시설을 숙소에 연결
            List<Convenience> conveniences = new ArrayList<>();
            conveniences.add(convenience1);
            conveniences.add(convenience2);
            hotel.setConvenience(conveniences);
            // 숙소 저장
            em.persist(hotel);

            // 트랜잭션 커밋
            tx.commit();
        } catch (Exception e) {
            // 롤백
            tx.rollback();
            e.printStackTrace();
        } finally {
            // 엔터티 매니저 종료
            em.close();

        }
    }

    // 2. 특정 기간에 정량 할인이나 정률 할인 적용
    public static void calPrice() {
        //모름
    }

    // 3. 조건에 맞는 숙소 조회
}
