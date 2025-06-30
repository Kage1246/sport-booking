/**
 * @author quangnv created on 3/20/2020
 */

package mdd.com.baseapp.dto.base;

import jakarta.validation.constraints.Min;
import lombok.Data;
import mdd.com.baseapp.common.Constant;


@Data
public class PageParam {

  @Min(value = 1)
  private Integer pageIndex = Constant.PAGE_NUM;

  @Min(value = 1)
  private Integer pageSize = Constant.PAGE_SIZE;

  private String operator;
}
