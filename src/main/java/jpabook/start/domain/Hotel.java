package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("HOTEL")
public class Hotel extends Address{
    @Id
    @GeneratedValue
    @Column(name = "HOTEL_ID")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String content;

    @Embedded
    private Price price;

    private int bedCount;

    private int toiletCount;

    private RoomType roomType;

    private int roomCount;

    @OneToMany(mappedBy="hotel")
    private List<Discount> discount;


    @ManyToMany
    private List<Convenience> convenience;

    @OneToMany(mappedBy="hotel")
    List<Review>reviews;

}
