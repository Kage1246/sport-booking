package mdd.com.baseapp.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import mdd.com.baseapp.controller.request.CommonRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public abstract class BaseRepositoryCustomImpl<E, RQ extends CommonRq> {

  private final Class<E> entityBeanType;
  @PersistenceContext
  private EntityManager em;

  public BaseRepositoryCustomImpl() {//Class<E> clz
    this.entityBeanType =
        ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
  }

  public Page<E> page(Pageable pageable, RQ req) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<E> cq = cb.createQuery(getEntityClass());
    Root<E> post = cq.from(getEntityClass());
    Predicate finalPredicate = createPredicate(cb, post, req);
    cq.where(finalPredicate);
    setOrderBy(cq, cb, post);  // Thêm thứ tự sắp xếp tại đây
    TypedQuery<E> query = em.createQuery(cq);
    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());
    List<E> resultList = query.getResultList();


    // Đếm tổng số lượng bản ghi (dùng cho phân trang)
    CriteriaBuilder cb2 = em.getCriteriaBuilder();
    CriteriaQuery<Long> countQuery = cb2.createQuery(Long.class);
    Root<E> post2 = countQuery.from(getEntityClass());
    Predicate countPredicate = createPredicate(cb2, post2, req);
    countQuery.select(cb2.count(post2));
    countQuery.where(countPredicate);
    Long totalCount = em.createQuery(countQuery).getSingleResult();

    // Tạo Page từ kết quả và thông tin phân trang
    return new PageImpl<>(resultList, pageable, totalCount);
  }

  public void setOrderBy(CriteriaQuery<E> cq, CriteriaBuilder cb, Root<E> root) {
    cq.orderBy(cb.asc(root.get("id"))); // Sắp xếp theo trường 'id' tăng dần
  }

  public abstract Predicate createPredicate(CriteriaBuilder cb, Root<E> post, RQ req);

  public Class<E> getEntityClass() {
    return this.entityBeanType;
  }
  //    public abstract Class<E> getEntityClass();
}
