package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Table(name = "fa_time_config_detail",
    indexes = {
        @Index(name = "idx_fa_time_config_detail_ftc_id", columnList = "faTimeConfigId")
    }
)
public class FaTimeConfigDetail extends AbstractAuditingEntity implements Serializable {
  @Column(nullable = false)
  private Integer faTimeConfigId;
  @Column
  private LocalTime startTime;
  @Column
  private LocalTime endTime;
  @Column
  private Integer stt;
  @Column
  private Integer day;
  @Column
  private StatusSlotEnum status;
}
