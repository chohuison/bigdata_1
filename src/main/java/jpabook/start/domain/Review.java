package jpabook.start.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue()
    @Column(name="REVIEW_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;
    @OneToOne
    @JoinColumn(name = "RESERVATION_ID")
    private ReservationStatus reservationStatus;

    private Integer star;

    private String review;
}
