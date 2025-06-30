package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fa_time_config",
    indexes = {
        @Index(name = "idx_fa_time_config_facility_id", columnList = "facilityId")
    }
)
public class FaTimeConfig extends AbstractAuditingEntity implements Serializable {
  @Column(nullable = false)
  private Integer facilityId;
  @Column
  private String name;
  @Column
  private String description;
  @Column
  private Integer period;
}
