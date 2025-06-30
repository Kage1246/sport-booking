package mdd.com.baseapp.controller;

import lombok.RequiredArgsConstructor;
import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.common.util.ResponseUtil;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.dto.FacilityDto;
import mdd.com.baseapp.dto.common.ResponseDto;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.service.interfaces.FacilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facility")
@RequiredArgsConstructor
public class FacilityController {

  private final FacilityService service;

  @PostMapping("/page")
  public ResponseEntity<ResponseDto> page(@RequestBody PageFilterInput<FacilityDto> params) {
    return ResponseUtil.ok(service.page(params));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto> getById(@PathVariable Integer id) {
    return ResponseUtil.ok(service.getActiveById(id));
  }

  @Secured({Constant.Permission.ADMIN})
  @PostMapping
  public ResponseEntity<ResponseDto> create(@RequestBody FacilityDto facilityDto) {
    facilityDto.setId(null);
    return ResponseUtil.created(service.createUpdate(facilityDto));
  }

  @Secured({Constant.Permission.MANAGER})
  @PutMapping()
  public ResponseEntity<ResponseDto> update(@RequestBody FacilityDto facilityDto) {
    if (facilityDto.getId() == null) {
      throw new CustomException("Id cơ sở không được để trống");
    }
    return ResponseUtil.ok(service.createUpdate(facilityDto));
  }

  @Secured({Constant.Permission.ADMIN})
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto> delete(@PathVariable Integer id) {
    return ResponseUtil.ok(service.delete(id));
  }
}
