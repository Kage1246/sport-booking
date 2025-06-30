package mdd.com.baseapp.mapper;

import java.util.List;
import mdd.com.baseapp.domain.AbstractAuditingEntity;
import org.mapstruct.MappingTarget;

@SuppressWarnings("checkstyle:InterfaceTypeParameterName")
public interface AbsMapper<E extends AbstractAuditingEntity, REQ, RES> {

  void updateEntity(REQ request, @MappingTarget E entity);

  RES toResponse(E e);

  List<RES> toResponse(List<E> e);

  E toEntity(REQ req);
}
