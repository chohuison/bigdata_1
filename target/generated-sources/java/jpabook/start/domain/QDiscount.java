package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiscount is a Querydsl query type for Discount
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDiscount extends EntityPathBase<Discount> {

    private static final long serialVersionUID = 341155989L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiscount discount = new QDiscount("discount");

    public final QDay _super = new QDay(this);

    public final EnumPath<DiscountType> discountType = createEnum("discountType", DiscountType.class);

    //inherited
    public final DatePath<java.time.LocalDate> finalDay = _super.finalDay;

    public final QHotel hotel;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DatePath<java.time.LocalDate> startDay = _super.startDay;

    public final NumberPath<Integer> value = createNumber("value", Integer.class);

    public QDiscount(String variable) {
        this(Discount.class, forVariable(variable), INITS);
    }

    public QDiscount(Path<? extends Discount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiscount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiscount(PathMetadata metadata, PathInits inits) {
        this(Discount.class, metadata, inits);
    }

    public QDiscount(Class<? extends Discount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotel = inits.isInitialized("hotel") ? new QHotel(forProperty("hotel"), inits.get("hotel")) : null;
    }

}

