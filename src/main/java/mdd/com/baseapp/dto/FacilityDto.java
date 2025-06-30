package mdd.com.baseapp.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mdd.com.baseapp.dto.common.AuditingDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDto extends AuditingDto {
  private String name;
  private String address;
  private String phoneNumber;
  private String description;
  private String owner;
  private String img;
  private LocalTime startTime;
  private LocalTime endTime;
}
