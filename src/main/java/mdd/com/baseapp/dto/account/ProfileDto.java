package mdd.com.baseapp.dto.account;

import lombok.Data;
import mdd.com.baseapp.common.enumpackage.GenderEnum;

@Data
public class ProfileDto {

  private String fullname;

  private String avatarUrl;

  private String accountNumber;


  private GenderEnum gender;

  private Integer status;

  private String gmail;

  private String balance;
}
