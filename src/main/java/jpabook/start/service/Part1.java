package jpabook.start.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.start.HotelDTO;
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
import java.util.Scanner;
import java.util.stream.Collectors;

public class Part1 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");


    // 숙소 등록
//    Address address = new Address();
//                        address.setCity("경주시");
//                        address.setStreet("한옥마을");
//                        address.setZipCode("3412");
//                        address.setZipCode("3412");
//    // 편의시설 생성
//    Convenience convenience1 = em.find(Convenience.class, 1L);
//    Convenience convenience2 = em.find(Convenience.class, 10L);
//    List<Convenience> convenienceList = new ArrayList<>();
//                        convenienceList.add(convenience1);
//                        convenienceList.add(convenience2);
//                        Part1.registHouse(6L, "숙소명", address, 1, 1,  1, "설명", convenienceList, 100000, 120000);
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


//                         System.out.println("---할인 전 가격---");
//                        Part1.calPrice(2L);
//                        Part1.applyDiscountPolicy(2L, DiscountType.QUANTITY, 10000, LocalDate.of(2023,11,21), LocalDate.of(2023,12,31));
//                        System.out.println("---할인 후 가격---");
//                        Part1.calPrice(2L);

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
    //    LocalDate checkinDate = LocalDate.of(2023,11,20);
//    LocalDate checkoutDate = LocalDate.of(2023,11,27);
//
//    // 한 가지 케이스 오름차순 or 내림차순으로 정렬, Order By 쿼리 사용
//                        Part1.findHouse(checkinDate, checkoutDate, 5, "Entire");
//                        System.out.println("-------------------------------");
//                        Part1.findHouse(checkinDate, checkoutDate, null, null);
//                        System.out.println("-------------------------------");
//                        Part1.findHouse(checkinDate, checkoutDate, 1, null);
    public static void findHouse(LocalDate checkinDate, LocalDate checkoutDate, Integer cnt, String houseType) // cnt: 인원 수(공간 전체), 방 개수(개인) / houseType은 Entire 또는 Individual
    {
        // houseType이 "Entire"일 때는 ENTIRE_HOTEL 조회 "Individual"일 때는 INDIVIDUAL_HOTEL 조회
        // checkinDate / checkoutDate 사이 예약 안되어있는 숙소 조회
        // 총 가격, 별점 평균 기준으로 내림차순 정렬 가능
        // 검색 조건에 맞는 숙소(숙소 유형, 이름, 총 가격, 평균 별점) 정보를 보여준다.

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QHotel hotel = QHotel.hotel;

        List<HotelDTO> hotels = new ArrayList<>();

        if (houseType == null || houseType.equals("Entire")) {
            JPAQuery<EntireHotel> query = new JPAQuery<>(em);
            QEntireHotel entireHotel = QEntireHotel.entireHotel;

            BooleanExpression expression = cnt != null ? entireHotel.maxCapacity.eq(cnt) : null;
            if (expression != null) {
                query = query.where(expression);
            }

            List<Long> ids = query.select(entireHotel.id)
                    .from(entireHotel)
                    .fetch();

            List<Hotel> entireHotels = queryFactory.selectFrom(hotel)
                    .where(
                            hotel.id.in(ids),
                            hotel.reservationStatuses.isEmpty()
                                    .or(hotel.reservationStatuses.any().startDay.goe(checkoutDate))
                                    .or(hotel.reservationStatuses.any().finalDay.loe(checkinDate))
                    )
                    .fetch();

            for (Hotel h : entireHotels) {
                int totalPrice = calculateTotalPrice(h, checkinDate, checkoutDate);
                double averageRating = calculateAverageRating(h);
                h.setTotalPrice(totalPrice);
                h.setAverageRating(averageRating);
                em.persist(h);
                HotelDTO hotelDTO = new HotelDTO(h.getId(), h.getName(), h.getContent(), h.getPrice(), h.getAddress(), h.getBedCount(), h.getToiletCount(), h.getDiscount(), h.getConvenience(), totalPrice, averageRating);
                hotelDTO.setRoomType("공간 전체");
                hotels.add(hotelDTO);
            }
        }

        if (houseType == null || houseType.equals("Individual")) {
            JPAQuery<IndividualHotel> query = new JPAQuery<>(em);
            QIndividualHotel individualHotel = QIndividualHotel.individualHotel;

            BooleanExpression expression = cnt != null ? individualHotel.roomCount.eq(cnt) : null;
            if (expression != null) {
                query = query.where(expression);
            }

            List<Long> ids = query.select(individualHotel.id)
                    .from(individualHotel)
                    .fetch();

            List<Hotel> individualHotels = queryFactory.selectFrom(hotel)
                    .where(
                            hotel.id.in(ids),
                            hotel.reservationStatuses.isEmpty()
                                    .or(hotel.reservationStatuses.any().startDay.goe(checkoutDate))
                                    .or(hotel.reservationStatuses.any().finalDay.loe(checkinDate))
                    )
                    .fetch();

            for (Hotel h : individualHotels) {
                int totalPrice = calculateTotalPrice(h, checkinDate, checkoutDate);
                double averageRating = calculateAverageRating(h);
                h.setTotalPrice(totalPrice);
                h.setAverageRating(averageRating);
                em.persist(h);
                HotelDTO hotelDTO = new HotelDTO(h.getId(), h.getName(), h.getContent(), h.getPrice(), h.getAddress(), h.getBedCount(), h.getToiletCount(), h.getDiscount(), h.getConvenience(), totalPrice, averageRating);
                hotelDTO.setRoomType("개인");
                hotels.add(hotelDTO);
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("정렬 기준을 선택해주세요. (1: 총 가격, 2: 별점 평균)");
        int sortType = sc.nextInt();


        OrderSpecifier<?> orderBy;
        if (sortType == 1) {
            orderBy = QHotel.hotel.totalPrice.desc();
        } else if (sortType == 2) {
            orderBy = QHotel.hotel.averageRating.desc();
        } else {
            System.out.println("잘못된 입력입니다. 기본적으로 총 가격으로 정렬합니다.");
            orderBy = QHotel.hotel.totalPrice.desc();
        }

        List<Long> hotelIds = hotels.stream().map(HotelDTO::getId).collect(Collectors.toList());
        List<Hotel> sortedHotels = queryFactory
                .selectFrom(QHotel.hotel)
                .where(QHotel.hotel.id.in(hotelIds))
                .orderBy(orderBy)
                .fetch();

        List<HotelDTO> sortedHotelDTOs = sortedHotels.stream()
                .map(h -> {
                    int totalPrice = calculateTotalPrice(h, checkinDate, checkoutDate);
                    double averageRating = calculateAverageRating(h);
                    HotelDTO hotelDTO = new HotelDTO(h.getId(), h.getName(), h.getContent(), h.getPrice(), h.getAddress(), h.getBedCount(), h.getToiletCount(), h.getDiscount(), h.getConvenience(), totalPrice, averageRating);
                    hotelDTO.setRoomType(hotels.stream().filter(dto -> dto.getId().equals(h.getId())).findFirst().orElse(new HotelDTO()).getRoomType());
                    return hotelDTO;
                })
                .collect(Collectors.toList());

        for (HotelDTO h : sortedHotelDTOs) {
            System.out.println(h.getRoomType() + " | " + h.getName() + " | " + h.getTotalPrice() + " | " + h.getAverageRating());
        }

        em.getTransaction().commit();

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
