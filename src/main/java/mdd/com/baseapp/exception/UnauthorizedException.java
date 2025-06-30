package mdd.com.baseapp.exception;

import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.common.constant.HttpStatusMessage;

public class UnauthorizedException extends CustomException {

  public UnauthorizedException() {
    this(HttpStatusMessage.UNAUTHORIZED);
  }

  public UnauthorizedException(String message) {
    super(message, null, HttpStatusCode.UNAUTHORIZED); // HttpStatus.UNAUTHORIZED = 401
  }
}
