package mdd.com.baseapp.repository.custom;

import mdd.com.baseapp.controller.request.CommonRq;
import mdd.com.baseapp.domain.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRepositoryCustom {
  Page<Chat> page(Pageable pageable, CommonRq req);
}
