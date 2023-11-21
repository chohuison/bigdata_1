package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVariablePrice is a Querydsl query type for VariablePrice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVariablePrice extends EntityPathBase<VariablePrice> {

    private static final long serialVersionUID = -1162812071L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVariablePrice variablePrice = new QVariablePrice("variablePrice");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final QHotel hotel;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QVariablePrice(String variable) {
        this(VariablePrice.class, forVariable(variable), INITS);
    }

    public QVariablePrice(Path<? extends VariablePrice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVariablePrice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVariablePrice(PathMetadata metadata, PathInits inits) {
        this(VariablePrice.class, metadata, inits);
    }

    public QVariablePrice(Class<? extends VariablePrice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotel = inits.isInitialized("hotel") ? new QHotel(forProperty("hotel"), inits.get("hotel")) : null;
    }

}

