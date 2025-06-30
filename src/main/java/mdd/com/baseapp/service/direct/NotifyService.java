package mdd.com.baseapp.service.direct;

import mdd.com.baseapp.controller.request.CommonRq;
import mdd.com.baseapp.domain.Notify;
import mdd.com.baseapp.dto.NotifyDto;
import mdd.com.baseapp.mapper.AbsMapper;
import mdd.com.baseapp.repository.custom.CustomJpa;
import mdd.com.baseapp.service.base.CrudService;
import org.springframework.stereotype.Service;

@Service
public class NotifyService extends CrudService<Notify, Integer, CommonRq, CommonRq, NotifyDto> {
  public NotifyService(CustomJpa<Notify, Integer> repository,
                       AbsMapper<Notify, CommonRq, NotifyDto> mapper) {
    super(repository, mapper);
  }
}
