package mdd.com.baseapp.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto extends AuditingDto {
  private Integer faTimeConfigId;
  private Integer facilityId;
  private Integer stt;
  private String img;
  private String name;
  private LocalTime startTime;
  private LocalTime endTime;
  private String description;
  private Double price;
  private Double minPrice;
  private Double maxPrice;
}
