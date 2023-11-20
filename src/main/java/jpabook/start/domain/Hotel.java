package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOTEL_ID")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String content;

    @Embedded
    private Price price;

    @Embedded
    private Address address;

    private int bedCount;

    private int toiletCount;



    @OneToMany(mappedBy="hotel")
    private List<Discount> discount;


    @ManyToMany
    private List<Convenience> convenience;

    @OneToMany(mappedBy="hotel")
    List<Review>reviews;

}
