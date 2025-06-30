/**
 * @author quangnv created on 3/19/2020
 */

package mdd.com.baseapp.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PageDto {

  @JsonProperty("content")
  @Default
  private List<?> contents = Collections.emptyList();

  @JsonProperty("total")
  private long total = 0;
}
