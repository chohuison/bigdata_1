package jpabook.start.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class VariablePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VARIABLE_ID")
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    private Integer price;

}
