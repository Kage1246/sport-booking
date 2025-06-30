package mdd.com.baseapp.dto;

import lombok.Data;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
public class ChatDto extends AuditingDto {
  private Integer type;
  private Integer fromUserId;
  private String fromUserName;
  private Integer toUserId;
  private String toUserName;
  private String content;
}