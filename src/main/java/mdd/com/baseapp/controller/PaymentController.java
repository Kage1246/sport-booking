package mdd.com.baseapp.controller;

import lombok.AllArgsConstructor;
import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.controller.request.VnpayReceive;
import mdd.com.baseapp.dto.common.ResponseDto;
import mdd.com.baseapp.service.direct.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {

  private final PaymentService service;

  @GetMapping("/getVnpayUrl")
  public ResponseEntity<ResponseDto> testPayment(String amount) {
    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data(service.getUrlNavigateVnpay(amount))
                .build());
  }

  @PostMapping("/p/vnpay/receive")
  public ResponseEntity<ResponseDto> receiveVnpay(@RequestBody VnpayReceive receive) {
    return ResponseEntity.ok()
        .body(ResponseDto.builder()
            .code(HttpStatusCode.OK)
            .message(Constant.Response.Message.OK)
            .data(service.receiveVnpay(receive))
            .build());
  }

  @GetMapping("/checkBalance")
  public ResponseEntity<ResponseDto> checkBalance() {
    return ResponseEntity.ok()
        .body(ResponseDto.builder()
            .code(HttpStatusCode.OK)
            .message(Constant.Response.Message.OK)
            .data(service.checkBalance())
            .build());
  }
}
