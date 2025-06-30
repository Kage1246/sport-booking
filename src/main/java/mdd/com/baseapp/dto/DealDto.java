package mdd.com.baseapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
public class DealDto extends AuditingDto implements Serializable {
  private Integer id;

  private Integer toUserId;

  private Integer postId;

  private String postName;

  private Integer fromUserId;
  private String fromUserName;
  private String fromUserAvtUrl;

  private String text;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate fromDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate toDate;

  private Integer status;

}
