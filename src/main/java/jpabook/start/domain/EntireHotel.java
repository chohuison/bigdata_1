package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("ENTIREHOTEL")
public class EntireHotel extends Hotel{
    private Integer maxCapacity;
}
