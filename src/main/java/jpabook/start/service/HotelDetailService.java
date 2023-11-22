package jpabook.start.service;


import jpabook.start.domain.*;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class HotelDetailService {
    EntityManager em;
    public HotelDetailService( EntityManager em){
        this.em=em;
    }
    //앤 그냥 편의시설 넣을라구..
//    public void hotelConvenice(){
//        Hotel hotel = em.find(Hotel.class, 1L);
//        List<Convenience>convenienceList = new ArrayList<>();
//        Convenience convenience = em.find(Convenience.class,1L);
//        convenienceList.add(convenience);
//        hotel.setConvenience(convenienceList);
//        em.flush();
//        em.merge(hotel);
//    }
    public void houseDetail(Long houseId, int month){
        Hotel hotel = em.find(Hotel.class, houseId);
        int num = 0;
        String type="";
        List<Review>reviewList = hotel.getReviews();
        List<Integer>stars = new ArrayList<>();
        List<String>reviwes = new ArrayList<>();
        for(int i=0;i< reviewList.size();i++){
            stars.add(reviewList.get(i).getStar());
            reviwes.add(reviewList.get(i).getReview());
        }
        List<Convenience>convenienceList = hotel.getConvenience();
        List<String>convenienceNames=new ArrayList<>();
        for(int i=0; i< convenienceList.size(); i++){
            convenienceNames.add(convenienceList.get(i).getName());
        }

        String arr[]=new String[32];

        try {
            TypedQuery<IndividualHotel> query1 = em.createQuery("SELECT ih FROM IndividualHotel ih WHERE ih.id = :hotelId", IndividualHotel.class);
            query1.setParameter("hotelId", houseId);
            IndividualHotel individualHotel = query1.getSingleResult();
            num= individualHotel.getRoomCount();
            type="개인 공간";
            arr = individualReservation(individualHotel,month);

        } catch (NoResultException e) {
            TypedQuery<EntireHotel> query2 = em.createQuery("SELECT eh FROM EntireHotel eh WHERE eh.id = :hotelId", EntireHotel.class);
            query2.setParameter("hotelId", houseId);
            EntireHotel entireHotel = query2.getSingleResult();
            num= entireHotel.getMaxCapacity();
            type="전체 공간";
            arr=entireReservation(entireHotel,month);
        }

        System.out.println("이름 : "+hotel.getName());
        System.out.println("주소 : "+ hotel.getAddress().getCity() +" " +hotel.getAddress().getStreet() + " " +hotel.getAddress().getZipCode());
        System.out.println("설명 : "+hotel.getContent());
        System.out.println("공간 타입 : "+type);
        System.out.println("평일 가격"+hotel.getPrice().getWeekdayPrice());
        System.out.println("주말 가격"+hotel.getPrice().getWeekdayPrice());
        System.out.print("편의시설 : ");
        for(int i=0; i< convenienceNames.size(); i++){
            System.out.print(convenienceNames.get(i));
            if(i!=convenienceNames.size()-1)
            System.out.print("/");
        }
        System.out.println();
        System.out.println("수용 인원 "+num+"명 침실"+hotel.getBedCount()+"개 침대"+hotel.getBedCount()+"개 욕실"+hotel.getToiletCount());

        System.out.println("별점과 리뷰");
        for(int i=0;i< stars.size();i++){
            System.out.print("별점 : "+stars.get(i)+" ");
            System.out.println("리뷰 : "+reviwes.get(i)+" ");
        }

        calender(arr,month);

    }

    public String []individualReservation(IndividualHotel individualHotel, int month){
        String[] array = new String [32];
        Arrays.fill(array, Integer.toString(individualHotel.getRoomCount()));
        YearMonth yearMonth = YearMonth.of(2023, month);
        int lastDay = yearMonth.lengthOfMonth();
        LocalDate startDay = LocalDate.of(2023, month, 1); // 시작일
        LocalDate finalDay = LocalDate.of(2023, month, lastDay); // 종료일
        TypedQuery<ReservationStatus> query = em.createQuery("SELECT rs FROM ReservationStatus rs WHERE rs.hotel = :hotel AND rs.startDay >= :startDay AND rs.startDay <= :finalDay", ReservationStatus.class);
        query.setParameter("hotel", individualHotel);
        query.setParameter("startDay", startDay);
        query.setParameter("finalDay", finalDay);
        List<ReservationStatus> reservationStatus = query.getResultList();
        for(int i=0;i<reservationStatus.size();i++){
            LocalDate start = reservationStatus.get(i).getStartDay();
            int startDayOfMonth = start.getDayOfMonth();
            LocalDate end =reservationStatus.get(i).getFinalDay();
            int finalDayOfMonth=0;
            if(end.getMonthValue()!=month){
                finalDayOfMonth=lastDay;
            }else{
                finalDayOfMonth = end.getDayOfMonth();
            }

            for(int j=startDayOfMonth; j<finalDayOfMonth+1 ; j++){
                int value = Integer.parseInt(array[j]);
                int resultValue = value - reservationStatus.get(i).getCnt();
                array[j]=Integer.toString(resultValue);
            }

        }
        LocalDate getFinal = LocalDate.of(2023, month, 1);
        TypedQuery<ReservationStatus> finalQuery = em.createQuery("SELECT rs FROM ReservationStatus rs WHERE rs.hotel = :hotel AND rs.finalDay >= :startDay ", ReservationStatus.class);
        finalQuery.setParameter("hotel", individualHotel);
        finalQuery.setParameter("startDay", getFinal);

        List<ReservationStatus> newReservationStatus = finalQuery.getResultList();
        for(int i=0;i<newReservationStatus.size();i++){
            LocalDate start = getFinal;
            int startDayOfMonth = start.getDayOfMonth();
            LocalDate end =newReservationStatus.get(i).getFinalDay();
            int finalDayOfMonth=end.getDayOfMonth();;

            for(int j=startDayOfMonth; j<finalDayOfMonth+1 ; j++){
                int value = Integer.parseInt(array[j]);
                int resultValue = value - newReservationStatus.get(i).getCnt();
                array[j]=Integer.toString(resultValue);
            }

        }
        return array;
    }

    public String[]entireReservation(EntireHotel entireHotel, int month){
        String [] array = new String[32];
        Arrays.fill(array,"*");
        YearMonth yearMonth = YearMonth.of(2023, month);
        int lastDay = yearMonth.lengthOfMonth();
        LocalDate startDay = LocalDate.of(2023, month, 1); // 시작일
        LocalDate finalDay = LocalDate.of(2023, month, lastDay); // 종료일
        TypedQuery<ReservationStatus> query = em.createQuery("SELECT rs FROM ReservationStatus rs WHERE rs.hotel = :hotel AND rs.startDay >= :startDay AND rs.startDay <= :finalDay", ReservationStatus.class);
        query.setParameter("hotel", entireHotel);
        query.setParameter("startDay", startDay);
        query.setParameter("finalDay", finalDay);
        List<ReservationStatus> reservationStatus = query.getResultList();

        for(int i=0;i<reservationStatus.size();i++){
            LocalDate start = reservationStatus.get(i).getStartDay();
            int startDayOfMonth = start.getDayOfMonth();
            LocalDate end =reservationStatus.get(i).getFinalDay();
            int finalDayOfMonth=0;
            if(end.getMonthValue()!=month){
                finalDayOfMonth=lastDay;
            }else{
                finalDayOfMonth = end.getDayOfMonth();
            }

            for(int j=startDayOfMonth; j<finalDayOfMonth+1 ; j++){

                array[j]="o";

            }

        }
        LocalDate getFinal = LocalDate.of(2023, month, 1);
        TypedQuery<ReservationStatus> finalQuery = em.createQuery("SELECT rs FROM ReservationStatus rs WHERE rs.hotel = :hotel AND rs.finalDay >= :startDay ", ReservationStatus.class);
        finalQuery.setParameter("hotel", entireHotel);
        finalQuery.setParameter("startDay", getFinal);

        List<ReservationStatus> newReservationStatus = finalQuery.getResultList();
        for(int i=0;i<newReservationStatus.size();i++){
            LocalDate start = getFinal;
            int startDayOfMonth = start.getDayOfMonth();
            LocalDate end =newReservationStatus.get(i).getFinalDay();
            int finalDayOfMonth=end.getDayOfMonth();;

            for(int j=startDayOfMonth; j<finalDayOfMonth+1 ; j++){
                array[j]="o";
            }

        }

        return array;
    }

    public void calender(String[] arr, int month) {
        // 해당 달의 첫 번째 날짜를 가져옴
        LocalDate firstDayOfMonth = LocalDate.of(2023, month, 1);

        // 해당 달의 첫 번째 날짜의 요일을 가져옴 (1: 월요일, 2: 화요일, ..., 7: 일요일)
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        System.out.println(firstDayOfWeek);
        // 해당 달의 마지막 날짜를 가져옴
        int lastDayOfMonth = firstDayOfMonth.lengthOfMonth();

        // 달력 출력
        System.out.println("[" + 2023 + "년 " + month + "월" + "]");
        System.out.println("일   월   화  수  목  금  토");

        // 첫 번째 주의 시작 위치 조정
        int dayCount =0;
        int arrCount =0;
        int arrLength=1;
        for (int i = 0; i < firstDayOfWeek; i++) {
            System.out.print("    ");
            dayCount++;
        }



        for (int day = 1; day <= lastDayOfMonth; day++) {
            System.out.printf("%2d  ", day);
            dayCount++;
            // 토요일마다 줄바꿈
            if (dayCount % 7 == 0) {


                System.out.println();
                if(arrCount ==0 ){
                    for (int i = 0; i < firstDayOfWeek; i++) {
                        System.out.print("    ");
                        arrCount++;
                    }
                }
                while(true){
                    System.out.print(" "+arr[arrLength]+"  ");
                    arrCount++;
                    arrLength++;
                    if(arrCount % 7 ==0){
                        break;
                    }
                }
                System.out.println();

            }



        }
        System.out.println();
        for(int i=arrLength;i<= lastDayOfMonth;i++){
            System.out.print(" "+arr[arrLength]+"  ");
        }
    }


}





