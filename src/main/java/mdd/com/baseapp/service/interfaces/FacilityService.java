package mdd.com.baseapp.service.interfaces;

import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.dto.FacilityDto;
import mdd.com.baseapp.dto.base.PageDto;

public interface FacilityService {


  PageDto page(PageFilterInput<FacilityDto> params);

  FacilityDto getActiveById(Integer id);

  FacilityDto createUpdate(FacilityDto facilityDto);

  Integer delete(Integer id);
}
