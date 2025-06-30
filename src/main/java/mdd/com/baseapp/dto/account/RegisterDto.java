package mdd.com.baseapp.dto.account;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class RegisterDto {
  private Integer id;

  @NotNull
  private String username;

  @NotNull
  private String fullname;

  private String avatarUrl;

  private String accountNumber;

  @NotNull
  private List<Integer> roleIds;

  @NotNull
  private String password;

  private Integer status;
}
