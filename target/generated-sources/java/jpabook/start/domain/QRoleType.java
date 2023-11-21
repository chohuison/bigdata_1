package jpabook.start.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoleType is a Querydsl query type for RoleType
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRoleType extends EnumPath<RoleType> {

    private static final long serialVersionUID = -198605788L;

    public static final QRoleType roleType = new QRoleType("roleType");

    public QRoleType(String variable) {
        super(RoleType.class, forVariable(variable));
    }

    public QRoleType(Path<RoleType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoleType(PathMetadata metadata) {
        super(RoleType.class, metadata);
    }

}

