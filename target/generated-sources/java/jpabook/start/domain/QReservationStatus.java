package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservationStatus is a Querydsl query type for ReservationStatus
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservationStatus extends EntityPathBase<ReservationStatus> {

    private static final long serialVersionUID = 619530762L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservationStatus reservationStatus = new QReservationStatus("reservationStatus");

    public final QDay _super = new QDay(this);

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    //inherited
    public final DatePath<java.time.LocalDate> finalDay = _super.finalDay;

    public final QHotel hotel;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DatePath<java.time.LocalDate> startDay = _super.startDay;

    public QReservationStatus(String variable) {
        this(ReservationStatus.class, forVariable(variable), INITS);
    }

    public QReservationStatus(Path<? extends ReservationStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservationStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservationStatus(PathMetadata metadata, PathInits inits) {
        this(ReservationStatus.class, metadata, inits);
    }

    public QReservationStatus(Class<? extends ReservationStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotel = inits.isInitialized("hotel") ? new QHotel(forProperty("hotel"), inits.get("hotel")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

