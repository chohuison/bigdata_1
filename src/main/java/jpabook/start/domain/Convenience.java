package jpabook.start.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity(name="CONVENIENCE")
@ToString
@NoArgsConstructor
public class Convenience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CONVENIENCE_ID")
    private Long id;

    private String name;

    private ConvenienceType convenienceType;

}
