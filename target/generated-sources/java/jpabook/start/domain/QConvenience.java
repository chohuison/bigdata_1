package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QConvenience is a Querydsl query type for Convenience
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConvenience extends EntityPathBase<Convenience> {

    private static final long serialVersionUID = 813850147L;

    public static final QConvenience convenience = new QConvenience("convenience");

    public final EnumPath<ConvenienceType> convenienceType = createEnum("convenienceType", ConvenienceType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QConvenience(String variable) {
        super(Convenience.class, forVariable(variable));
    }

    public QConvenience(Path<? extends Convenience> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConvenience(PathMetadata metadata) {
        super(Convenience.class, metadata);
    }

}

