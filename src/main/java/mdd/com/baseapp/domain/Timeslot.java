package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mdd.com.baseapp.common.enumpackage.StatusSlotEnum;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "timeslot", indexes = {
    @Index(name = "idx_timeslot_unit_date_detail", columnList = "unitId, date, faTimeConfigDetailId")
})
public class Timeslot extends AbstractAuditingEntity implements Serializable {
  @Column(nullable = false)
  private Integer unitId;
  @Column(nullable = false)
  private Integer faTimeConfigDetailId;
  @Column(nullable = false)
  private LocalDate day;
  @Column
  private LocalTime startTime;
  @Column
  private LocalTime endTime;
  @Column
  private Integer stt;
  @Column
  private StatusSlotEnum status;
}
