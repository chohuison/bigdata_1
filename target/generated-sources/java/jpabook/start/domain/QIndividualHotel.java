package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIndividualHotel is a Querydsl query type for IndividualHotel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QIndividualHotel extends EntityPathBase<IndividualHotel> {

    private static final long serialVersionUID = -420014905L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIndividualHotel individualHotel = new QIndividualHotel("individualHotel");

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

    public final NumberPath<Integer> roomCount = createNumber("roomCount", Integer.class);

    //inherited
    public final NumberPath<Integer> toiletCount;

    public QIndividualHotel(String variable) {
        this(IndividualHotel.class, forVariable(variable), INITS);
    }

    public QIndividualHotel(Path<? extends IndividualHotel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIndividualHotel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIndividualHotel(PathMetadata metadata, PathInits inits) {
        this(IndividualHotel.class, metadata, inits);
    }

    public QIndividualHotel(Class<? extends IndividualHotel> type, PathMetadata metadata, PathInits inits) {
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

