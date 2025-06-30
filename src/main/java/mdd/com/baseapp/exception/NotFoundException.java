package mdd.com.baseapp.exception;

import lombok.Getter;
import lombok.Setter;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.common.constant.HttpStatusMessage;

@Getter
@Setter
public class NotFoundException extends CustomException {

  public NotFoundException() {
    super(HttpStatusMessage.NOT_FOUND, null, HttpStatusCode.NOT_FOUND);
  }

  public NotFoundException(String message) {
    super(message, null, HttpStatusCode.NOT_FOUND);
  }

  public NotFoundException(String message, Object data) {
    super(message, data, HttpStatusCode.NOT_FOUND);
  }
}