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
    @GeneratedValue
    @Column(name="CONVENIENCE_ID")
    private Long id;

    private String name;

    private ConvenienceType convenienceType;

    public Convenience(String name, ConvenienceType convenienceType) {
        this.name = name;
        this.convenienceType = convenienceType;
    }
}
