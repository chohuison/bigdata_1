package jpabook.start.service;


import jpabook.start.domain.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public  class HotelDetailService {
//    EntityManager em;
//    public HotelDetailService( EntityManager em){
//        this.em=em;
//    }
//    public void houseDetail(Long houseId, int month){
//        Hotel hotel = em.find(Hotel.class, houseId);
//        System.out.println(hotel.getName());
//
//        System.out.println("이름 : ");
//        System.out.println("주소 : "+ hotel.getCity() +" " +hotel.getStreet() + " " +hotel.getZipCode());
//        System.out.print("편의시설 : ");
//        for(int i=0; i< hotel.getConvenience().size(); i++){
//            Convenience convenience = hotel.getConvenience().get(i);
//            System.out.print(convenience.getName()+"/");
//        }
//        System.out.println();
//        System.out.println("수용 인원 "+hotel.getRoomCount()+"명 침실"+hotel.getBedCount()+"개 침대"+hotel.getBedCount()+"개 욕실"+hotel.getToiletCount());
//        System.out.println("별점과 리뷰");
//        List<Review>reviewList = hotel.getReviews();
//        for(int i=0;i< reviewList.size();i++){
//            System.out.print(reviewList.get(i).getStar());
//            System.out.println(reviewList.get(i).getReview());
//        }
//
//    }


}
