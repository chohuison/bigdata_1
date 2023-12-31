package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Discount extends Day{

    @Id
    @GeneratedValue
    @Column(name="DISCOUNT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @Enumerated(value = EnumType.STRING)
    private DiscountType discountType;

    private Integer value;

}


