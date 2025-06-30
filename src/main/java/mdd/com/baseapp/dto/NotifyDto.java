package mdd.com.baseapp.dto;

import java.io.Serializable;
import lombok.Data;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
public class NotifyDto extends AuditingDto implements Serializable {
  private Integer id;
  private Integer userId;
  private String title;
  private String body;
  private Integer type;
  private Integer refId;
}
