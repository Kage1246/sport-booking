package mdd.com.baseapp.dto.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditingDto implements Serializable, Cloneable {
  private Integer id;
  private String createdBy;

  private String lastModifiedBy;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime createdDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime lastModifiedDate;

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
