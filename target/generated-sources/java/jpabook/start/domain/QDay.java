package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDay is a Querydsl query type for Day
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QDay extends EntityPathBase<Day> {

    private static final long serialVersionUID = -1479306296L;

    public static final QDay day = new QDay("day");

    public final DatePath<java.time.LocalDate> finalDay = createDate("finalDay", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> startDay = createDate("startDay", java.time.LocalDate.class);

    public QDay(String variable) {
        super(Day.class, forVariable(variable));
    }

    public QDay(Path<? extends Day> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDay(PathMetadata metadata) {
        super(Day.class, metadata);
    }

}

