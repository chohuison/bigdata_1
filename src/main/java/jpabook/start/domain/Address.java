package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_ID")
    private Long id;

    private String city;
    private String street;
    private String zipCode;
}
