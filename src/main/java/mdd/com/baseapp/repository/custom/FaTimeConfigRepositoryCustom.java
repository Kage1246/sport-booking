package mdd.com.baseapp.repository.custom;


import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.FaTimeConfig;
import mdd.com.baseapp.dto.FaTimeConfigDto;
import org.springframework.data.domain.Page;

public interface FaTimeConfigRepositoryCustom {
  Page<FaTimeConfig> page(PageFilterInput<FaTimeConfigDto> pageFilterInput);
}
