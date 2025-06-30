package mdd.com.baseapp.dto;

import java.io.Serializable;
import java.util.LinkedList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaTimeConfigDto implements Serializable {
  private Integer id;
  private Integer facilityId;
  private String name;
  private String description;
  private Integer period;
  private LinkedList<FaTimeConfigDetailDto> configDetails;
}
