package jpabook.start.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    private DiscountType discountType;

    private int value;

}
