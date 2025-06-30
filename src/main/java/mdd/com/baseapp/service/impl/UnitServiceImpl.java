package mdd.com.baseapp.service.impl;

import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.Unit;
import mdd.com.baseapp.dto.UnitDto;
import mdd.com.baseapp.dto.base.PageDto;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.exception.NotFoundException;
import mdd.com.baseapp.security.SecurityUtils;
import mdd.com.baseapp.service.base.BaseAutowire;
import mdd.com.baseapp.service.interfaces.UnitService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends BaseAutowire implements UnitService {
  @Override
  public PageDto page(PageFilterInput<UnitDto> params) {
    Page<Unit> page = unitRepository.page(params);
    return PageDto.builder().contents(unitMapper.toResponse(page.getContent()))
        .total(page.getTotalElements()).build();
  }

  @Override
  public UnitDto getActiveById(Integer id) {
    Unit unit = unitRepository.findByIdAndIsDeleteFalse(id).orElse(null);
    return unitMapper.toResponse(unit);
  }

  @Override
  public UnitDto createUpdate(UnitDto unitDto) {

    // Gán facilityId nếu không phải admin
    if (SecurityUtils.isNotAdmin()) {
      unitDto.setFacilityId(SecurityUtils.getCurrentUser().getUser().getFacilityId());
    }

    // Check facilityId
    if (unitDto.getFacilityId() == null) {
      throw new CustomException("Facility id is null. Please check your request.");
    }

    // Kiểm tra facility có tồn tại không
    facilityRepository.findByIdAndIsDeleteFalse(unitDto.getFacilityId())
        .orElseThrow(
            () -> new NotFoundException("Không tìm thấy cơ sở  ID = " + unitDto.getFacilityId()));

    // Check faTimeConfig
    if (unitDto.getFaTimeConfigId() != null) {
      faTimeConfigRepository.findByIdAndFacilityIdAndIsDeleteFalse(unitDto.getFaTimeConfigId(),
              unitDto.getFacilityId())
          .orElseThrow(() -> new NotFoundException(
              "Không tìm thấy cấu hình với ID = " + unitDto.getFaTimeConfigId()));
    }

    Unit unit;
    if (unitDto.getId() != null) {
      unit = unitRepository.findByIdAndIsDeleteFalse(unitDto.getId()).orElseThrow(
          () -> new NotFoundException("Không tìm thấy sân với ID = " + unitDto.getId()));
    } else {
      unit = new Unit();
    }
    unitMapper.updateEntity(unitDto, unit);
    unitRepository.save(unit);
    return unitMapper.toResponse(unit);
  }

  @Override
  public Integer delete(Integer id) {
    Unit unit = unitRepository.findByIdAndFacilityIdAndIsDeleteFalse(id,
        SecurityUtils.getCurrentUser().getUser().getFacilityId()).orElse(null);
    if (unit != null) {
      unit.setIsDelete(true);
      unitRepository.save(unit);
      return id;
    }
    return null;
  }
}
