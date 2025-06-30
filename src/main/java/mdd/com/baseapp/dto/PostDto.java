package mdd.com.baseapp.dto;

import java.util.List;
import lombok.Data;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
public class PostDto extends AuditingDto {
  private Integer id;
  private String imgUrls;
  private String avatarUrl;
  private String title;
  private String body;
  private String preText;
  private Integer userId;
  private String userName;
  private Integer status;
  private List<CommentDto> comments;
  private List<TagDto> tag;
  private Integer likeCount;
  private Integer likeValue;
}
