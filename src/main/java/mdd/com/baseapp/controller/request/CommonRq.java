package mdd.com.baseapp.controller.request;

import java.util.List;
import lombok.Data;
import mdd.com.baseapp.dto.base.PageParam;

@Data
public class CommonRq extends PageParam {
  private String keyword;

  private Boolean isOwner;

  private Boolean isActive;

  private Integer postId;

  private Integer userId;

  private Integer commentId;

  private Integer toUserId;

  private Integer status;

  private String sortType;

  private String sortBy;

  private List<Integer> tag;
}
