package mdd.com.baseapp.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNameNotFoundExceptionCustom extends AuthenticationException {

  public UserNameNotFoundExceptionCustom(String msg, Throwable cause) {
    super(msg, cause);
  }

  public UserNameNotFoundExceptionCustom() {
    super("Không tìm thấy người dùng này");
  }
}
