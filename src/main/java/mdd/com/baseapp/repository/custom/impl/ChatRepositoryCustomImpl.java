package mdd.com.baseapp.repository.custom.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import mdd.com.baseapp.controller.request.CommonRq;
import mdd.com.baseapp.domain.Chat;
import mdd.com.baseapp.repository.custom.ChatRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRepositoryCustomImpl extends BaseRepositoryCustomImpl<Chat, CommonRq>
    implements ChatRepositoryCustom {

  @Override
  public Predicate createPredicate(CriteriaBuilder cb, Root<Chat> chat, CommonRq req) {
    List<Predicate> predicates = new ArrayList<>();
    Predicate predicate1 = cb.equal(chat.get("toUserId"), req.getToUserId());
    Predicate predicate2 = cb.equal(chat.get("fromUserId"), req.getUserId());
    Predicate predicate12 = cb.and(predicate1, predicate2);

    Predicate predicate3 = cb.equal(chat.get("toUserId"), req.getUserId());
    Predicate predicate4 = cb.equal(chat.get("fromUserId"), req.getToUserId());
    Predicate predicate34 = cb.and(predicate3, predicate4);

    Predicate predicate1234 = cb.or(predicate12, predicate34);
    predicates.add(predicate1234);

    //        if (req.getStatus() != null) {
    //            predicates.add(cb.equal(chat.get("status"), req.getStatus()));
    //        }
    return cb.and(predicates.toArray(new Predicate[0]));
  }

  @Override
  public void setOrderBy(CriteriaQuery<Chat> cq, CriteriaBuilder cb, Root<Chat> root) {
    cq.orderBy(cb.desc(root.get("createdDate")));
  }
}