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
public class Member {

    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;


}
