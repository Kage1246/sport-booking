package mdd.com.baseapp.dto;

import lombok.Data;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
public class TagDto extends AuditingDto {
  private Integer id;
  private String text;
}
