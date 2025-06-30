package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mdd.com.baseapp.common.enumpackage.StatusSlotEnum;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends AbstractAuditingEntity implements Serializable {

  @Column(nullable = false)
  private Integer timeSlotId;
  @Column
  private LocalTime startTime;
  @Column
  private LocalTime endTime;
  @Column
  private Integer stt;
  @Column
  private Integer day;
  @Column
  private String name;
  @Column
  private String phoneNumber;
  @Column
  private String gmail;
  @Column
  private String price;
  @Enumerated(EnumType.STRING)
  private StatusSlotEnum status;
}
