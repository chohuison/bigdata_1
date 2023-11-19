package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Setter
@Getter
public class Price {
    private int weekdayPrice;
    private int weekendPrice;
}
