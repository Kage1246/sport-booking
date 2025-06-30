package mdd.com.baseapp.service.interfaces;

import jakarta.transaction.Transactional;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.dto.FaTimeConfigDto;
import mdd.com.baseapp.dto.base.PageDto;

public interface FaTimeConfigService {
  PageDto page(PageFilterInput<FaTimeConfigDto> pageFilterInput);

  FaTimeConfigDto getById(Integer id);

  @Transactional
  FaTimeConfigDto create(FaTimeConfigDto faTimeConfigDto);

  @Transactional
  FaTimeConfigDto update(Integer id, FaTimeConfigDto faTimeConfigDto);

  void deleteById(Integer id);
}
