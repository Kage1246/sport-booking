// ResponseUtil.java

package mdd.com.baseapp.common.util;

import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.common.constant.HttpStatusMessage;
import mdd.com.baseapp.dto.common.ResponseDto;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
  public static ResponseEntity<ResponseDto> ok(String message) {
    return ResponseEntity.ok(
        ResponseDto.builder().code(HttpStatusCode.OK).message(message).build());
  }

  public static ResponseEntity<ResponseDto> ok(Object data) {
    return ResponseEntity.ok(
        ResponseDto.builder().code(HttpStatusCode.OK).message(Constant.Response.Message.OK)
            .data(data).build());
  }

  public static ResponseEntity<ResponseDto> ok(Object data, String message) {
    return ResponseEntity.ok(
        ResponseDto.builder().code(HttpStatusCode.OK).message(message).data(data).build());
  }

  public static <T> ResponseEntity<ResponseDto> created(T data) {
    return ResponseEntity.status(HttpStatusCode.CREATED).body(
        ResponseDto.<T>builder().code(HttpStatusCode.CREATED).message(HttpStatusMessage.CREATED)
            .data(data).build());
  }

  public static <T> ResponseEntity<ResponseDto> created(T data, String message) {
    return ResponseEntity.status(HttpStatusCode.CREATED).body(
        ResponseDto.<T>builder().code(HttpStatusCode.CREATED).message(message).data(data).build());
  }

  public static <T> ResponseEntity<ResponseDto> error(String message, int status) {
    return ResponseEntity.status(status)
        .body(ResponseDto.<T>builder().code(status).message(message).build());
  }
}
