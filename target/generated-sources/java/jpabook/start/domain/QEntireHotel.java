package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntireHotel is a Querydsl query type for EntireHotel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEntireHotel extends EntityPathBase<EntireHotel> {

    private static final long serialVersionUID = 934716623L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntireHotel entireHotel = new QEntireHotel("entireHotel");

    public final QHotel _super;

    // inherited
    public final QAddress address;

    //inherited
    public final NumberPath<Integer> bedCount;

    //inherited
    public final StringPath content;

    //inherited
    public final ListPath<Convenience, QConvenience> convenience;

    //inherited
    public final ListPath<Discount, QDiscount> discount;

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Integer> maxCapacity = createNumber("maxCapacity", Integer.class);

    // inherited
    public final QMember member;

    //inherited
    public final StringPath name;

    // inherited
    public final QPrice price;

    //inherited
    public final ListPath<ReservationStatus, QReservationStatus> reservationStatuses;

    //inherited
    public final ListPath<Review, QReview> reviews;

    //inherited
    public final NumberPath<Integer> toiletCount;

    public QEntireHotel(String variable) {
        this(EntireHotel.class, forVariable(variable), INITS);
    }

    public QEntireHotel(Path<? extends EntireHotel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntireHotel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntireHotel(PathMetadata metadata, PathInits inits) {
        this(EntireHotel.class, metadata, inits);
    }

    public QEntireHotel(Class<? extends EntireHotel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QHotel(type, metadata, inits);
        this.address = _super.address;
        this.bedCount = _super.bedCount;
        this.content = _super.content;
        this.convenience = _super.convenience;
        this.discount = _super.discount;
        this.id = _super.id;
        this.member = _super.member;
        this.name = _super.name;
        this.price = _super.price;
        this.reservationStatuses = _super.reservationStatuses;
        this.reviews = _super.reviews;
        this.toiletCount = _super.toiletCount;
    }

}

