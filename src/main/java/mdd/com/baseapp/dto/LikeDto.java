package mdd.com.baseapp.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class LikeDto implements Serializable {
  private Integer id;

  private Integer userId;

  private Integer commentId;

  private Integer value;

  private Integer postId;

}
