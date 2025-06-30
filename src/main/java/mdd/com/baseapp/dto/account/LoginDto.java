package mdd.com.baseapp.dto.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

  @NotNull(message = "Không được bỏ trống")
  @Size(min = 1, max = 50, message = "Username phải từ 1-50 ký tự")
  private String username;
  @NotNull(message = "Không được bỏ trống")
  @Size(min = 4, max = 100, message = "Password phải từ 4-50 ký tự")
  private String password;

  private boolean rememberMe;

  public LoginDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "LoginVM{" +
        "username='" + username + '\'' +
        ", rememberMe=" + rememberMe +
        '}';
  }
}
