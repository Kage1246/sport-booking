package mdd.com.baseapp.service.base;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.domain.AbstractAuditingEntity;
import mdd.com.baseapp.dto.base.PageDto;
import mdd.com.baseapp.dto.base.PageParam;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.mapper.AbsMapper;
import mdd.com.baseapp.repository.custom.CustomJpa;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("ALL")
@Slf4j
@Transactional
@NoRepositoryBean
public class CrudService<E extends AbstractAuditingEntity, ID, P extends PageParam, REQ, RES extends Serializable>
    extends BaseAutowire {

  public final CustomJpa<E, ID> repository;
  public final AbsMapper<E, REQ, RES> mapper;
  private final Class<E> entityBeanType;


  public CrudService(CustomJpa<E, ID> repository, AbsMapper<E, REQ, RES> mapper) {
    this.repository = repository;
    this.mapper = mapper;
    this.entityBeanType =
        ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

    log.info("Init service CRUD with entity: {}", entityBeanType.getName());
  }


  public PageDto page(P params) {

    final Page<E> page = repository.findAll(new Specification<E>() {
      @SneakyThrows

      public Predicate toPredicate(Root<E> root, CriteriaQuery<?> criteriaQuery,
                                   CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        final Map<String, Object> keys = new HashMap<>();
        for (Field field : params.getClass().getDeclaredFields()) {
          field.setAccessible(true);
          keys.put(field.getName(), field.get(params));
        }
        keys.forEach((k, v) -> {
          if (v != null) {
            if (v instanceof String) {
              predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(k)),
                  "%" + ((String) v).trim().toUpperCase() + "%"));
            } else {
              predicates.add(criteriaBuilder.equal(root.get(k), v));
            }
          }
        });

        if (!predicates.isEmpty()) {
          if ("and".equals(params.getOperator())) {
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
          } else {
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
          }
        }
        return null;
      }
    }, getPageable(params));

    return PageDto.builder().contents(page.map(this::toResponse).getContent())
        .total(page.getTotalElements()).build();
  }

  public E findById(ID id) {
    return repository.findById(id).orElseThrow(
        () -> new CustomException("Not found data ID: " + id, null, HttpStatusCode.NOT_FOUND));
  }


  public E findActiveById(ID id) {

    final E e = findById(id);

    if (BooleanUtils.isFalse(e.getIsDelete())) {
      throw new CustomException("Not found data ID: " + id, null, HttpStatusCode.NOT_FOUND);
    }

    return e;
  }


  public RES getById(ID id) {
    return toResponse(findById(id));
  }


  public RES getActiveById(ID id) {

    final E e = findActiveById(id);

    return toResponse(e);
  }


  public List<RES> getAll() {
    return repository.findAll().stream()
        //        .filter(f -> BooleanUtils.isTrue(f.getIsActive()))
        .map(this::toResponse).collect(Collectors.toList());
  }


  public List<RES> getAllActive() {
    return repository.findAll().stream().filter(f -> BooleanUtils.isTrue(f.getIsDelete()))
        .map(this::toResponse).collect(Collectors.toList());
  }


  public List<RES> getAllActiveWithZoneCluster(P p) {
    return null;
  }


  public RES toResponse(E e) {
    return mapper.toResponse(e);
  }

  public List<RES> toResponse(List<E> e) {
    return mapper.toResponse(e);
  }


  @SneakyThrows
  public RES create(REQ request) {

    final E e = entityBeanType.newInstance();
    beforeSave(request, e);
    mapToEntity(request, e);
    E e2 = repository.save(e);
    afterSave(request, e2);
    //    userLogRepository.save(
    //        UserLog.builder().model(entityBeanType.getName()).action(ActionEnum.INSERT).build());
    return toResponse(e2);
    //        return e2;
  }


  @SneakyThrows
  public RES update(ID id, REQ request) {
    final E e = findActiveById(id);
    final Object clone = ((AbstractAuditingEntity) e).clone();
    beforeSave(request, e);
    mapToEntity(request, e);
    E e2 = repository.save(e);
    afterSave(request, e2);
    return toResponse(e2);
  }


  public E changeActive(ID id) {
    final E e = findById(id);
    e.setIsDelete(!e.getIsDelete());
    repository.save(e);
    afterChangeActive(e);
    return e;
  }


  public E delete(ID id) {
    final E e = findById(id);
    return deleteEntity(e);
  }


  public E deleteEntity(E e) {
    beforeDelete(e);
    repository.delete(e);
    afterDelete(e);
    return e;
  }


  public void mapToEntity(REQ request, E e) {
    mapper.updateEntity(request, e);
  }


  public void afterSave(REQ request, E e) {
  }


  public void afterChangeActive(E e) {
  }


  public void afterDelete(E e) {
  }


  public void beforeSave(REQ request, E e) {
  }


  public void beforeDelete(E e) {
  }


  public Pageable getPageable(PageParam pageParam) {
    return PageRequest.of(pageParam.getPageIndex() - 1, pageParam.getPageSize(),
        Sort.Direction.DESC, "createdDate");
  }

  public JpaRepository<E, ID> getJpaRepository() {
    return repository;
  }

  public AbsMapper<E, REQ, RES> getMapper() {
    return mapper;
  }
}
