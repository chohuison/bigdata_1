package jpabook.start;

import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jpabook.start.JpaMain.emf;

public class Part569
{
    public static void bookHouse(Long guestId, Long hotelId,
                                 LocalDate checkinDate, LocalDate checkoutDate,
                                 String roomType,int numOfPerson)
    {
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

            ReservationStatus reservationStatus = new ReservationStatus();
            reservationStatus.setHotel(hotel);
            reservationStatus.setStartDay(checkinDate);
            reservationStatus.setFinalDay(checkoutDate);
            Member guest = em.find(Member.class, guestId);
            reservationStatus.setMember(guest);

            // 개별 호텔 예약
            if ("INDIVIDUAL".equals(roomType)) {
                if (hotel instanceof IndividualHotel) {
                    IndividualHotel individualHotel = (IndividualHotel) hotel;
                    if (numOfPerson <= individualHotel.getRoomCount()) {
                        reservationStatus.setCnt(numOfPerson);
                        em.persist(reservationStatus);
                        System.out.println("예약이 완료되었습니다.");
                        ((IndividualHotel) hotel).setRoomCount(((IndividualHotel) hotel).getRoomCount()-numOfPerson);
                    } else {
                        System.out.println("인원이 숙소의 최대 수용 가능 인원을 초과했습니다.");
                    }
                } else {
                    System.out.println("개별 호텔이 아닌 호텔은 INDIVIDUAL 예약이 불가능합니다.");
                }
            }
            // 전체 호텔 예약
            else if ("ENTIRE".equals(roomType)) {
                if (hotel instanceof EntireHotel) {
                    EntireHotel entireHotel = (EntireHotel) hotel;
                    if (numOfPerson <= entireHotel.getMaxCapacity()) {
                        reservationStatus.setCnt(numOfPerson);
                        em.persist(reservationStatus);
                        System.out.println("예약이 완료되었습니다.");
                        ((EntireHotel) hotel).setMaxCapacity(((EntireHotel) hotel).getMaxCapacity()-numOfPerson);
                    } else {
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
