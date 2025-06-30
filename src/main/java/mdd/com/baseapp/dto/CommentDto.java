package mdd.com.baseapp.dto;

import java.io.Serializable;
import lombok.Data;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
public class CommentDto extends AuditingDto implements Serializable {
  private String userName;

  private int userId;

  private int postId;

  private String text;

  private String imgUrl;

  private Integer likeCount;

  private Integer likeValue;
}
