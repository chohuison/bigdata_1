package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPrice is a Querydsl query type for Price
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPrice extends BeanPath<Price> {

    private static final long serialVersionUID = 32401013L;

    public static final QPrice price = new QPrice("price");

    public final NumberPath<Integer> weekdayPrice = createNumber("weekdayPrice", Integer.class);

    public final NumberPath<Integer> weekendPrice = createNumber("weekendPrice", Integer.class);

    public QPrice(String variable) {
        super(Price.class, forVariable(variable));
    }

    public QPrice(Path<? extends Price> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrice(PathMetadata metadata) {
        super(Price.class, metadata);
    }

}

