package mdd.com.baseapp.repository.custom;

import mdd.com.baseapp.controller.request.CommonRq;
import mdd.com.baseapp.domain.Notify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotifyRepositoryCustom {
  Page<Notify> page(Pageable pageable, CommonRq req);
}
