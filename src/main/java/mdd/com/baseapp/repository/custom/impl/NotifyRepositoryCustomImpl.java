package mdd.com.baseapp.repository.custom.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import mdd.com.baseapp.controller.request.CommonRq;
import mdd.com.baseapp.domain.Notify;
import mdd.com.baseapp.repository.custom.NotifyRepositoryCustom;

public class NotifyRepositoryCustomImpl
    extends BaseRepositoryCustomImpl<Notify, CommonRq>
    implements NotifyRepositoryCustom {
  @Override
  public Predicate createPredicate(CriteriaBuilder cb, Root<Notify> notify, CommonRq req) {
    List<Predicate> predicates = new ArrayList<>();
    //        if (req.getPostId() != null) {
    //            predicates.add(cb.equal(notify.get("postId"), req.getPostId()));
    //        }
    //        if (req.getToUserId() != null) {
    //            predicates.add(cb.equal(notify.get("toUserId"), req.getToUserId()));
    //        } else predicates.add(cb.equal(notify.get("toUserId"), SecurityUtils.getCurrentUserId()));
    //
    //        if (req.getStatus() != null) {
    //            predicates.add(cb.equal(notify.get("status"), req.getStatus()));
    //        }
    return cb.and(predicates.toArray(new Predicate[0]));
  }

  @Override
  public Class<Notify> getEntityClass() {
    return Notify.class;
  }

  @Override
  public void setOrderBy(CriteriaQuery<Notify> cq, CriteriaBuilder cb, Root<Notify> root) {
    cq.orderBy(cb.desc(root.get("id")));
  }
}
