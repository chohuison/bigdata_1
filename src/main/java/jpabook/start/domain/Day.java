package jpabook.start.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
public class Day {

    private LocalDate startDay;
    private LocalDate finalDay;
}
