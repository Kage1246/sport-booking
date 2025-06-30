package mdd.com.baseapp.repository.custom;

import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.Unit;
import mdd.com.baseapp.dto.UnitDto;
import org.springframework.data.domain.Page;

public interface UnitRepositoryCustom {
  Page<Unit> page(PageFilterInput<UnitDto> params);
}
