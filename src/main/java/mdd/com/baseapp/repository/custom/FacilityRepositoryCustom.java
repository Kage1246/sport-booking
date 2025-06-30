package mdd.com.baseapp.repository.custom;

import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.Facility;
import mdd.com.baseapp.dto.FacilityDto;
import org.springframework.data.domain.Page;

public interface FacilityRepositoryCustom {
  Page<Facility> page(PageFilterInput<FacilityDto> params);
}
