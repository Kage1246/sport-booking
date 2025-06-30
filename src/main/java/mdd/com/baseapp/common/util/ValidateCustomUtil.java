package mdd.com.baseapp.common.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

public class ValidateCustomUtil<O> {
  private final Validator validator;

  // Constructor để inject Validator vào class
  public ValidateCustomUtil(Validator validator) {
    this.validator = validator;
  }

  public String validate(O dto) {
    BindingResult bindingResult = new BeanPropertyBindingResult(dto, "dto");
    // Thực hiện kiểm tra hợp lệ của đối tượng POJO
    this.validator.validate(dto, bindingResult);

    List<String> errorMessages = new ArrayList<>();
    if (bindingResult.hasErrors()) {
      for (FieldError error : bindingResult.getFieldErrors()) {
        // Lấy thông báo lỗi cho mỗi lỗi
        String errorMessage = error.getDefaultMessage();
        errorMessages.add(errorMessage);
      }
    }
    return String.join(", ", errorMessages);
  }
}
