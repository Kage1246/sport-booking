package mdd.com.baseapp.exception;


import static mdd.com.baseapp.common.constant.HttpStatusMessage.METHOD_NOT_ALLOWED;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.common.constant.HttpStatusMessage;
import mdd.com.baseapp.dto.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class ExceptionControllerHandler {


  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<ResponseDto> handleAuthenticationException(Exception ex) {
    ResponseDto responseDto =
        new ResponseDto("Đăng nhập thất bại", ex.getMessage(), HttpStatusCode.UNAUTHORIZED);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ResponseDto> handleCustomException(CustomException ex) {
    ResponseDto responseDto = new ResponseDto(ex.getMessage(), ex.getData(), ex.getCode());
    return ResponseEntity.status(HttpStatus.valueOf(ex.getCode())).body(responseDto);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ResponseDto> handleMethodNotSupportedExceptions(Exception ex) {
    ResponseDto responseDto =
        new ResponseDto(METHOD_NOT_ALLOWED, ex.getMessage(), HttpStatusCode.METHOD_NOT_ALLOWED);
    log.error(ex.getMessage(), (Object) ex.getStackTrace());
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(responseDto);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
    ResponseDto responseDto =
        new ResponseDto(HttpStatusMessage.FORBIDDEN, ex.getMessage(), HttpStatusCode.FORBIDDEN);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ResponseDto> handleBindException(BindException ex) {
    List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
    ResponseDto responseDto =
        new ResponseDto(errorList.get(0), errorList, HttpStatusCode.BAD_REQUEST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
  }

  // JSON parse error
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    ResponseDto responseDto = new ResponseDto("Request body không hợp lệ",
        ex.getMostSpecificCause().getMessage(), HttpStatusCode.BAD_REQUEST);
    return ResponseEntity
        .badRequest()
        .body(responseDto);
  }

  // Field không hợp lệ hoặc validation lỗi
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining("; "));
    ResponseDto responseDto = new ResponseDto("Validation lỗi",
        message, HttpStatusCode.BAD_REQUEST);
    return ResponseEntity.badRequest().body(responseDto);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseDto> handleOtherExceptions(Exception ex) {
    ResponseDto responseDto =
        new ResponseDto(HttpStatusMessage.INTERNAL_SERVER_ERROR, ex.getMessage(),
            HttpStatusCode.INTERNAL_SERVER_ERROR);
    log.error(ex.getMessage(), (Object) ex.getStackTrace());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
  }
}