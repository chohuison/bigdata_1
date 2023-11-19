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
    @GeneratedValue
    @Column(name="REVIEW_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    private int star;

    private String review;
}
