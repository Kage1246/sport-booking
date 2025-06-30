package mdd.com.baseapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.common.util.ResponseUtil;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.dto.UnitDto;
import mdd.com.baseapp.dto.common.ResponseDto;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.service.interfaces.UnitService;
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
@RequestMapping("/api/unit")
@RequiredArgsConstructor
public class UnitController {
  private final UnitService service;

  @PostMapping("/page")
  public ResponseEntity<ResponseDto> page(@RequestBody PageFilterInput<UnitDto> params) {
    return ResponseUtil.ok(service.page(params));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto> getById(@PathVariable Integer id) {
    return ResponseUtil.ok(service.getActiveById(id));
  }

  @Secured({Constant.Permission.MANAGER})
  @PostMapping
  public ResponseEntity<ResponseDto> create(@Valid @RequestBody UnitDto unitDto) {
    unitDto.setId(null);
    return ResponseUtil.created(service.createUpdate(unitDto), "Tạo sân thành công");
  }

  @Secured({Constant.Permission.MANAGER})
  @PutMapping()
  public ResponseEntity<ResponseDto> update(@Valid @RequestBody UnitDto unitDto) {
    if (unitDto.getId() == null) {
      throw new CustomException("Id sân không được để trống");
    }
    return ResponseUtil.ok(service.createUpdate(unitDto), "Cập nhật sân thành công");
  }

  @Secured({Constant.Permission.MANAGER})
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto> delete(@PathVariable Integer id) {
    return ResponseUtil.ok(service.delete(id), "Xóa sân thành công");
  }
}
