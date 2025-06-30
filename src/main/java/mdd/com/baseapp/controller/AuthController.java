package mdd.com.baseapp.controller;

import lombok.AllArgsConstructor;
import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.dto.common.ResponseDto;
import mdd.com.baseapp.service.direct.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
  private final EmailService emailService;

  @GetMapping("/send-otp")
  public ResponseEntity<ResponseDto> sendOtp() {
    emailService.sendEmailToUser();
    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .build());
  }


  @GetMapping("/confirm-otp")
  public ResponseEntity<ResponseDto> confirmOtp(String otp) {
    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data(emailService.activeAccount(otp))
                .build());
  }
}
