package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ReservationStatus extends Day {
    @Id
    @GeneratedValue()
    @Column(name = "RESERVATION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    private Integer cnt;//예약한 방(개인공간)or예약한 인원(전체공간)

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Integer totalPrice;

    private boolean isReview;
}
