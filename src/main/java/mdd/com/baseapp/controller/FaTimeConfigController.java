package mdd.com.baseapp.controller;

import mdd.com.baseapp.common.util.ResponseUtil;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.dto.FaTimeConfigDto;
import mdd.com.baseapp.dto.common.ResponseDto;
import mdd.com.baseapp.service.impl.FaTimeConfigServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/faTimeConfigs")
public class FaTimeConfigController {
  private final FaTimeConfigServiceImpl faTimeConfigServiceImpl;

  public FaTimeConfigController(FaTimeConfigServiceImpl faTimeConfigServiceImpl) {
    this.faTimeConfigServiceImpl = faTimeConfigServiceImpl;
  }

  @PostMapping("/paging")
  public ResponseEntity<ResponseDto> getTimeConfigPaging(
      @RequestBody PageFilterInput<FaTimeConfigDto> pageFilterInput) {
    return ResponseUtil.ok(faTimeConfigServiceImpl.page(pageFilterInput));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto> getTimeConfig(@PathVariable Integer id) {
    return ResponseUtil.ok(faTimeConfigServiceImpl.getById(id));
  }

  @PostMapping("")
  public ResponseEntity<ResponseDto> createTimeConfig(@RequestBody FaTimeConfigDto faTimeConfig) {
    return ResponseUtil.created(faTimeConfigServiceImpl.create(faTimeConfig),
        "Tạo mới cầu hình thành công");
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseDto> updateTimeConfig(@PathVariable Integer id,
                                                      @RequestBody FaTimeConfigDto faTimeConfig) {
    return ResponseUtil.ok(faTimeConfigServiceImpl.update(id, faTimeConfig));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDto> deleteTimeConfig(@PathVariable Integer id) {
    faTimeConfigServiceImpl.deleteById(id);
    return ResponseUtil.ok("Đã xóa cấu hình");
  }
}
