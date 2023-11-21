package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotel is a Querydsl query type for Hotel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHotel extends EntityPathBase<Hotel> {

    private static final long serialVersionUID = 24934112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotel hotel = new QHotel("hotel");

    public final QAddress address;

    public final NumberPath<Integer> bedCount = createNumber("bedCount", Integer.class);

    public final StringPath content = createString("content");

    public final ListPath<Convenience, QConvenience> convenience = this.<Convenience, QConvenience>createList("convenience", Convenience.class, QConvenience.class, PathInits.DIRECT2);

    public final ListPath<Discount, QDiscount> discount = this.<Discount, QDiscount>createList("discount", Discount.class, QDiscount.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath name = createString("name");

    public final QPrice price;

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final NumberPath<Integer> toiletCount = createNumber("toiletCount", Integer.class);

    public QHotel(String variable) {
        this(Hotel.class, forVariable(variable), INITS);
    }

    public QHotel(Path<? extends Hotel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotel(PathMetadata metadata, PathInits inits) {
        this(Hotel.class, metadata, inits);
    }

    public QHotel(Class<? extends Hotel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.price = inits.isInitialized("price") ? new QPrice(forProperty("price")) : null;
    }

}

