package mdd.com.baseapp.exception;

import lombok.Getter;
import lombok.Setter;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.common.constant.HttpStatusMessage;

@Getter
@Setter
public class CustomException extends RuntimeException {
  private String message = HttpStatusMessage.BAD_REQUEST;

  private Integer code = HttpStatusCode.BAD_REQUEST;

  private Object data;

  public CustomException() {
    super();
  }

  public CustomException(String message) {
    super(message);
    this.message = message;
  }

  public CustomException(String message, Object data) {
    super(message);
    this.message = message;
    this.data = data;
  }

  public CustomException(String message, Object data, Integer code) {
    super(message);
    this.message = message;
    this.data = data;
    this.code = code;
  }

}
