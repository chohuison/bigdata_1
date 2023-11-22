package jpabook.start;

import jpabook.start.domain.Address;
import jpabook.start.domain.Convenience;
import jpabook.start.domain.Discount;
import jpabook.start.domain.Price;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HotelDTO {

    private Long id;
    private String name;
    private String content;
    private String roomType;
    private Price price;
    private Address address;
    private Integer bedCount;
    private Integer toiletCount;
    private List<Discount> discount;
    private List<Convenience> convenience;
    private int totalPrice;
    private double averageRating;

    // Constructor, Getter, Setter 생략

    public HotelDTO(Long id, String name, String content, Price price, Address address, Integer bedCount, Integer toiletCount, List<Discount> discount, List<Convenience> convenience, int totalPrice, double averageRating) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
        this.address = address;
        this.bedCount = bedCount;
        this.toiletCount = toiletCount;
        this.discount = discount;
        this.convenience = convenience;
        this.totalPrice = totalPrice;
        this.averageRating = averageRating;
    }

    public HotelDTO() {

    }
}
