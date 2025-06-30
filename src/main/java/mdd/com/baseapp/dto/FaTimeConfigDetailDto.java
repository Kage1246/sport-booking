package mdd.com.baseapp.dto;

import java.io.Serializable;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mdd.com.baseapp.common.enumpackage.StatusSlotEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaTimeConfigDetailDto implements Serializable {
  private Integer id;
  private Integer faTimeConfigId;
  private LocalTime startTime;
  private LocalTime endTime;
  private Integer stt;
  private Integer day;
  private StatusSlotEnum status;
}
