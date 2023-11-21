package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("INDIVIDUALHOTEL")
public class IndividualHotel extends Hotel{
    private Integer roomCount;
}
