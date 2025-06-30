package mdd.com.baseapp.controller;

import lombok.AllArgsConstructor;
import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.dto.common.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TestController {
  @Secured({Constant.Permission.CUSTOMER_ONLY, Constant.Permission.MANAGER})
  @GetMapping("/test")
  public ResponseEntity<ResponseDto> testPayment(String amount) {
    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data("He he")
                .build());
  }
}
