package jpabook.start;

import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static jpabook.start.JpaMain.emf;

//            Part569.pushStartData();
//                    //5번
//                    LocalDate checkinDate = LocalDate.now();
//                    LocalDate checkoutDate = LocalDate.now().plusDays(3);
//                    LocalDate cIn = LocalDate.of(2023,11,21);
//                    LocalDate cOut = LocalDate.of(2023,11,22);
//                    LocalDate cIn2 = LocalDate.of(2023,11,20);
//                    LocalDate cOut2 = LocalDate.of(2023,11,22);
//                    Part569.bookHouse(29L,34L,cIn2,cOut2,"INDIVIDUAL",5);
//                    Part569.bookHouse(29L,34L,cIn,cOut,"INDIVIDUAL",3);
//                    //6번
////            Part569.cancelReserve(36L);
//                    //9번
//                    Part569.showReserveState(34L,11);
//                    Part569.calRevenue(34L,11);

public class Part569
{

    //5번 숙소예약
    public static void bookHouse(Long guestId, Long hotelId,
                                 LocalDate checkinDate, LocalDate checkoutDate,
                                 String roomType, int numOfPerson) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Day day = new Day();
        day.setStartDay(checkinDate);
        day.setFinalDay(checkoutDate);

        try {
            tx.begin();
            // 숙소 조회
            Hotel hotel = em.find(Hotel.class, hotelId);
            if (hotel == null) {
                System.out.println("존재하지 않는 숙소입니다.");
                return;
            }

            // 기존 예약 조회
            List<ReservationStatus> existingReservations = em.createQuery("SELECT r FROM ReservationStatus r " +
                            "WHERE r.hotel = :hotel " +
                            "AND r.startDay <= :checkoutDate " +
                            "AND r.finalDay >= :checkinDate", ReservationStatus.class)
                    .setParameter("hotel", hotel)
                    .setParameter("checkinDate", checkinDate)
                    .setParameter("checkoutDate", checkoutDate)
                    .getResultList();

            ReservationStatus reservationStatus = new ReservationStatus();
            reservationStatus.setHotel(hotel);
            reservationStatus.setStartDay(checkinDate);
            reservationStatus.setFinalDay(checkoutDate);
            Member guest = em.find(Member.class, guestId);
            reservationStatus.setMember(guest);

            int days = calculateTotalPrice(hotel,checkinDate,checkoutDate);
            reservationStatus.setTotalPrice(days);
            // 개별 호텔 예약
            if ("INDIVIDUAL".equals(roomType)) {
                if (hotel instanceof IndividualHotel) {
                    int numOfPersonReservations = existingReservations.stream()
                            .mapToInt(ReservationStatus::getCnt)
                            .sum();

                    if(((IndividualHotel) hotel).getRoomCount() >=numOfPersonReservations + numOfPerson)
                    {
                        reservationStatus.setCnt(numOfPersonReservations + numOfPerson);
                        if (numOfPerson <= reservationStatus.getCnt()) {
                            em.persist(reservationStatus);
                            System.out.println("예약이 완료되었습니다.");
                            System.out.println("======>예약된 cnt : " + reservationStatus.getCnt());
                        }
                    }
                    else {
                        System.out.println("인원이 숙소의 최대 수용 가능 인원을 초과했습니다.");
                    }
                } else {
                    System.out.println("개별 호텔이 아닌 호텔은 INDIVIDUAL 예약이 불가능합니다.");
                }
            }
            // 전체 호텔 예약
            else if ("ENTIRE".equals(roomType)) {
                if (hotel instanceof EntireHotel) {
                    int numOfPersonReservations = existingReservations.stream()
                            .mapToInt(ReservationStatus::getCnt)
                            .sum();

                    if(((EntireHotel) hotel).getMaxCapacity() >= numOfPersonReservations + numOfPerson)
                    {
                        reservationStatus.setCnt(((EntireHotel) hotel).getMaxCapacity());
                        if (numOfPerson <= reservationStatus.getCnt()) {
                            em.persist(reservationStatus);
                            System.out.println("예약이 완료되었습니다.");
                            System.out.println("======>예약된 cnt : " + reservationStatus.getCnt());
                        }
                    }
                    else {
                        System.out.println("인원이 숙소의 최대 수용 가능 인원을 초과했습니다.");
                    }
                } else {
                    System.out.println("전체 호텔이 아닌 호텔은 ENTIRE 예약이 불가능합니다.");
                }
            } else {
                System.out.println("=====roomType 오류, roomType은 INDIVIDUAL or ENTIRE======");
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("예약 중 오류 발생: " + e.getMessage());
        } finally {
            em.close();
        }
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

    //6번 숙소 예약 취소
    public static void cancelReserve(Long reserveId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // 예약 조회
            ReservationStatus reservation = em.find(ReservationStatus.class, reserveId);
            if (reservation == null) {
                System.out.println("존재하지 않는 예약입니다.");
                return;
            }
            // 예약이 취소되면 해당 방의 개수를 복구
            Hotel hotel = reservation.getHotel();
            if (hotel instanceof IndividualHotel) {
                IndividualHotel individualHotel = (IndividualHotel) hotel;
                individualHotel.setRoomCount(individualHotel.getRoomCount() + reservation.getCnt());
            } else if (hotel instanceof EntireHotel) {
                EntireHotel entireHotel = (EntireHotel) hotel;
                entireHotel.setMaxCapacity(entireHotel.getMaxCapacity() + reservation.getCnt());
            }

            // 예약 삭제
            em.remove(reservation);
            System.out.println("예약이 취소되었습니다.");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("예약 취소 중 오류 발생: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    //9번 호스트 달 매출 확인
    public static void showReserveState(Long hostId, int month) {
        EntityManager em = emf.createEntityManager();
        try {
            List<ReservationStatus> reservations = em.createQuery(
                            "SELECT r FROM ReservationStatus r " +
                                    "WHERE r.hotel.member.id = :hostId " , ReservationStatus.class)
                    .setParameter("hostId", hostId)
                    .getResultList();
            List<ReservationStatus> resultReservations = new ArrayList<>();
            for(ReservationStatus reservation:reservations)
            {
                if(reservation.getStartDay().getMonthValue() == month)
                    resultReservations.add(reservation);
            }
            // 콘솔에 예약 상세 정보를 출력
            for (ReservationStatus reservation : resultReservations) {
                System.out.println("=========================================");
                System.out.println("예약 ID : " + reservation.getId());
                System.out.println("예약날짜 : " + reservation.getStartDay() + " ~ " + reservation.getFinalDay());
                System.out.println("총 숙박비 : " + reservation.getTotalPrice());
            }
        } finally {
            em.close();
        }
    }
    public static void calRevenue(Long hostId, int month) {
        EntityManager em = emf.createEntityManager();
        try {
            LocalDate currentDate = LocalDate.now();
            List<ReservationStatus> reservations = em.createQuery(
                            "SELECT r FROM ReservationStatus r " +
                                    "WHERE r.hotel.member.id = :hostId " +
                                    "AND r.finalDay <= :currentDate", ReservationStatus.class)
                    .setParameter("hostId", hostId)
                    .setParameter("currentDate", currentDate)
                    .getResultList();

            List<ReservationStatus> resultReservations = new ArrayList<>();
            for (ReservationStatus reservation : reservations) {
                if (reservation.getStartDay().getMonthValue() == month) {
                    resultReservations.add(reservation);
                    System.out.println(reservation);
                }
            }
            // 총 매출을 계산
            int totalRevenue = 0;
            for (ReservationStatus reservation : resultReservations) {
                totalRevenue += reservation.getTotalPrice();
            }
            // 콘솔에 총 매출을 출력
            System.out.println("***해당 월의 총 매출: " + totalRevenue);
        } finally {
            em.close();
        }
    }

    public static void pushStartData()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            //편의시설 추가==========================
            ConvenienceSetting.convenienceSetting();
            //====사람추가===========================
            Member g1 = new Member("안",RoleType.GUEST);
            Member g2 = new Member("소",RoleType.GUEST);
            Member g3 = new Member("정",RoleType.GUEST);
            Member g4 = new Member("안소정",RoleType.GUEST);
            em.persist(g1);
            em.persist(g2);
            em.persist(g3);
            em.persist(g4);
            Member h1 = new Member("김",RoleType.HOST);
            Member h2 = new Member("성",RoleType.HOST);
            Member h3 = new Member("렬",RoleType.HOST);
            Member h4 = new Member("김성렬",RoleType.HOST);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.persist(h4);
            //====숙소===========================
            IndividualHotel hotel1 = new IndividualHotel();
            hotel1.setName("한옥스테이\"화응\"");
            hotel1.setMember(h4);
            hotel1.setContent("흙은 사라지고 아스팔트와 콘크리트 건물들 사이에 살아있는 화응(花應)을 아스팔트에 피어있는 꽃으로 비유하여 도심 속의 꽃 \"화응(花應)\" 이란 이름이 만들어졌습니다.\n" +
                    "\n" +
                    "화응을 관통하는 꽃을 형상화하기 위하여 과거 한국적 꽃 이미지를 형상화했던 자료들을 바탕으로 음과 양, 좌우 대칭되는 미 와 현대적 미를 놓치지 않으며 간결하고 명료하게 표현하였습니다.");
            Price price = new Price();
            price.setWeekdayPrice(50000);
            price.setWeekendPrice(80000);
            hotel1.setPrice(price);
            Address address = new Address();
            address.setCity("전주");
            address.setStreet("완산구");
            address.setZipCode("태평동");
            hotel1.setAddress(address);
            hotel1.setBedCount(10);
            hotel1.setRoomCount(10);
            List<Convenience> convenienceList = new ArrayList<>();
            Convenience convenience = em.find(Convenience.class,1L);
            convenienceList.add(convenience);
            hotel1.setConvenience(convenienceList);
            em.persist(hotel1);

            em.flush();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        System.out.println("=======기본 데이터 등록 완료=======");

    }
}
