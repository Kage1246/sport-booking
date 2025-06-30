package mdd.com.baseapp.service.interfaces;

import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.dto.UnitDto;
import mdd.com.baseapp.dto.base.PageDto;

public interface UnitService {
  PageDto page(PageFilterInput<UnitDto> params);

  UnitDto getActiveById(Integer id);

  UnitDto createUpdate(UnitDto facilityDto);

  Integer delete(Integer id);
}
