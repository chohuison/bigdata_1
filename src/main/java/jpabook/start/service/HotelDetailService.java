package jpabook.start.service;


import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
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
        Hotel hotel = em.find(Hotel.class, 1L);
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

        try {
            TypedQuery<IndividualHotel> query1 = em.createQuery("SELECT ih FROM IndividualHotel ih WHERE ih.id = :hotelId", IndividualHotel.class);
            query1.setParameter("hotelId", 1L);
            IndividualHotel individualHotel = query1.getSingleResult();
            num= individualHotel.getRoomCount();
            type="개인 공간";

        } catch (NoResultException e) {

        }
        try {
            TypedQuery<EntireHotel> query2 = em.createQuery("SELECT eh FROM EntireHotel eh WHERE eh.id = :hotelId", EntireHotel.class);
            query2.setParameter("hotelId", 1L);
            EntireHotel entireHotel = query2.getSingleResult();
            num= entireHotel.getMaxCapacity();
            type="전체 공간";
        } catch (NoResultException e) {

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

    }


}
