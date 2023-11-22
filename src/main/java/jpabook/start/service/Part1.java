package jpabook.start.service;

import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Part1 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");


    // 숙소 등록
    public static void registHouse(Member host, String name, Address address, int roomCount, int bedCnt, int toiletCnt, String content, List<Convenience> convenienceList, int weekdayPrice, int weekendPrice) {
        //이름, 주소, 소유자(호스트), 수용 인원, 침실/침대/욕실 개수, 숙소 소개, 숙소 편의시설, 요금 정책(평일과 주말) 등록
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            if (host == null) {
                System.out.println("Host not found");
                return;
            }

            // 숙소 생성
            IndividualHotel hotel = new IndividualHotel();
            hotel.setName(name);
            hotel.setMember(host);
            hotel.setContent(content);
            hotel.setAddress(address);
            hotel.setBedCount(bedCnt);
            hotel.setToiletCount(toiletCnt);
            hotel.setRoomCount(roomCount);
            hotel.setConvenience(convenienceList);
            hotel.setPrice(new Price(weekdayPrice, weekendPrice)); // 주말과 평일 가격
            em.persist(hotel);

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


    // 2. 요금 확인
    public static void calPrice(Hotel hotel) {

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            int weekdayPrice = hotel.getPrice().getWeekdayPrice();
            int weekendPrice = hotel.getPrice().getWeekendPrice();
            System.out.println("현재 호텔 가격: (평일): " + weekdayPrice +", (주말):" + weekendPrice);

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
    
    // 3. 요금 적용
    public static void applyDiscount(Hotel hotel, DiscountType discountType, int value, LocalDate startDay, LocalDate finalDay) {

        LocalDate now = LocalDate.now();

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Discount discountAmount = new Discount();
            discountAmount.setHotel(hotel);
            discountAmount.setDiscountType(discountType);
            discountAmount.setValue(value);
            discountAmount.setStartDay(startDay);
            discountAmount.setFinalDay(finalDay);
            em.persist(discountAmount);

            // 할인 적용 여부를 확인하고 적용
            if ((discountAmount.getStartDay().isBefore(now) && discountAmount.getFinalDay().isAfter(now)) || discountAmount.getStartDay().equals(now) || discountAmount.getFinalDay().equals(now) ) {
                int newWeekdayPrice = hotel.getPrice().getWeekdayPrice();
                int newWeekendPrice = hotel.getPrice().getWeekendPrice();
                if (discountType.equals(DiscountType.RATE))
                {
                    newWeekdayPrice = (int)(newWeekdayPrice * (1 - value / 100.0));
                    newWeekendPrice = (int)(newWeekendPrice * (1 - value / 100.0));
                }
                else {
                    newWeekdayPrice = newWeekdayPrice - discountAmount.getValue();
                    newWeekendPrice = newWeekendPrice - discountAmount.getValue();
                }
                hotel.setPrice(new Price(newWeekdayPrice, newWeekendPrice));
            }

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
