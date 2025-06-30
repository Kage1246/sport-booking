package mdd.com.baseapp.service.impl;

import java.util.Objects;
import lombok.AllArgsConstructor;
import mdd.com.baseapp.common.constant.HttpStatusMessage;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.Facility;
import mdd.com.baseapp.dto.FacilityDto;
import mdd.com.baseapp.dto.base.PageDto;
import mdd.com.baseapp.exception.NotFoundException;
import mdd.com.baseapp.security.CustomUserDetail;
import mdd.com.baseapp.security.SecurityUtils;
import mdd.com.baseapp.service.base.BaseAutowire;
import mdd.com.baseapp.service.interfaces.FacilityService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FacilityServiceImpl extends BaseAutowire implements FacilityService {


  @Override
  public PageDto page(PageFilterInput<FacilityDto> params) {
    Page<Facility> page = facilityRepository.page(params);
    return PageDto.builder().contents(facilityMapper.toResponse(page.getContent()))
        .total(page.getTotalElements()).build();
  }

  @Override
  public FacilityDto getActiveById(Integer id) {
    Facility facility = facilityRepository.findByIdAndIsDeleteFalse(id).orElse(null);
    return facilityMapper.toResponse(facility);
  }

  @Override
  public FacilityDto createUpdate(FacilityDto facilityDto) {
    if (SecurityUtils.isNotAdmin()) {
      checkQuyenFacility(facilityDto.getId());
    }
    Facility facility;
    if (facilityDto.getId() != null) {
      facility = facilityRepository.findByIdAndIsDeleteFalse(facilityDto.getId())
          .orElseThrow(NotFoundException::new);
    } else {
      facility = new Facility();
    }
    facilityMapper.updateEntity(facilityDto, facility);
    facilityRepository.save(facility);
    return facilityMapper.toResponse(facility);
  }

  private void checkQuyenFacility(
      Integer id) { // Đảm bảo thằng user đó thuộc facility đó. Con quyen M hay C k quan tam
    if (id != null) {
      CustomUserDetail userDetail =
          (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (!Objects.equals(userDetail.getUser().getFacilityId(), id)) {
        throw new AccessDeniedException(HttpStatusMessage.FORBIDDEN);
      }
    }
  }

  @Override
  public Integer delete(Integer id) {
    Facility facility = facilityRepository.findByIdAndIsDeleteFalse(id).orElse(null);
    if (facility != null) {
      facility.setIsDelete(true);
      facilityRepository.save(facility);
      return id;
    }
    return null;
  }
}
