package mdd.com.baseapp.dto.account;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private Integer id;

  private String username;

  private String fullname;

  private String avatarUrl;

  private String accountNumber;

  private Integer status;

  private String jwt;

  private Double balance;

  private Set<String> permisssion;
}
