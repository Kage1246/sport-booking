package mdd.com.baseapp.dto;

import java.io.Serializable;
import lombok.Data;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
public class ContactDto extends AuditingDto implements Serializable {
  private Integer id;
  private Integer fromUserId;
  private Integer toUserId;
  private String toUserName;
  private String avtUrl;
}
