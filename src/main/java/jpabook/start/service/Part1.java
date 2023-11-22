package jpabook.start.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    //     3. 조건에 맞는 숙소 조회
    public static void findHouse(LocalDate checkinDate, LocalDate checkoutDate, Integer cnt, String houseType) // cnt: 인원 수(공간 전체), 방 개수(개인) / houseType은 Entire 또는 Individual
    {
        // houseType이 "Entire"일 때는 ENTIRE_HOTEL 조회 "Individual"일 때는 INDIVIDUAL_HOTEL 조회
        // checkinDate / checkoutDate 사이 예약 안되어있는 숙소 조회
        // 총 가격, 별점 평균 기준으로 내림차순 정렬 가능
        // 검색 조건에 맞는 숙소(숙소 유형, 이름, 총 가격, 평균 별점) 정보를 보여준다.

        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QHotel hotel = QHotel.hotel;


        List<Hotel> hotels = new ArrayList<>();

        if (houseType == null || houseType.equals("Entire")) {
            JPAQuery<EntireHotel> query = new JPAQuery<>(em);
            QEntireHotel entireHotel = QEntireHotel.entireHotel;

            BooleanExpression expression = cnt != null ? entireHotel.maxCapacity.goe(cnt) : null;
            if (expression != null) {
                query = query.where(expression);
            }

            List<Long> ids = query.select(entireHotel.id)
                    .from(entireHotel)
                    .fetch();

            hotels.addAll(queryFactory.selectFrom(hotel)
                    .where(
                            hotel.id.in(ids),
                            hotel.reservationStatuses.isEmpty()
                                    .or(hotel.reservationStatuses.any().startDay.goe(checkoutDate))
                                    .or(hotel.reservationStatuses.any().finalDay.loe(checkinDate))
                    )
                    .fetch());
        }

        if (houseType == null || houseType.equals("Individual")) {
            JPAQuery<IndividualHotel> query = new JPAQuery<>(em);
            QIndividualHotel individualHotel = QIndividualHotel.individualHotel;

            BooleanExpression expression = cnt != null ? individualHotel.roomCount.goe(cnt) : null;
            if (expression != null) {
                query = query.where(expression);
            }

            List<Long> ids = query.select(individualHotel.id)
                    .from(individualHotel)
                    .fetch();

            hotels.addAll(queryFactory.selectFrom(hotel)
                    .where(
                            hotel.id.in(ids),
                            hotel.reservationStatuses.isEmpty()
                                    .or(hotel.reservationStatuses.any().startDay.goe(checkoutDate))
                                    .or(hotel.reservationStatuses.any().finalDay.loe(checkinDate))
                    )
                    .fetch());
        }


        System.out.println("숙소 유형 | 이름 | 총 가격 | 평균 별점");

        for (Hotel h : hotels) {
            int totalPrice = calculateTotalPrice(h, checkinDate, checkoutDate);
            double averageRating = calculateAverageRating(h);

            String roomType = "";
            if (h instanceof EntireHotel) {
                roomType = "전체 공간";
            } else if (h instanceof IndividualHotel) {
                roomType = "개인";
            } else {
                roomType = "없음";
            }

            System.out.println(roomType + " | "+ h.getName() + " | " + totalPrice + " | " + averageRating + " | ");
        }


        em.close();
    }

    private static int calculateTotalPrice(Hotel hotel, LocalDate checkinDate, LocalDate checkoutDate) {
        int totalPrice = 0;
        LocalDate currentDate = checkinDate;
        while (currentDate.isBefore(checkoutDate)) {
            int price;
            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                price = hotel.getPrice().getWeekendPrice();
            } else {
                price = hotel.getPrice().getWeekdayPrice();
            }
            totalPrice += price;
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }
        return totalPrice;
    }


    private static double calculateAverageRating(Hotel hotel) {
        EntityManager em = emf.createEntityManager();

        List<Review> reviews = hotel.getReviews();
        if (reviews.isEmpty())
            return 0;

        double sum = 0;

        for (Review review : reviews)
            sum += review.getStar();

        em.close();
        return sum / reviews.size();

    }

}
