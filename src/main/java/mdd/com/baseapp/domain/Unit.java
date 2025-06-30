package mdd.com.baseapp.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unit"
    , indexes = {
    @Index(name = "idx_unit_facility_id", columnList = "facilityId")
}
)
public class Unit extends AbstractAuditingEntity implements Serializable {
  @ManyToOne
  @JoinColumn(name = "faTimeConfigId", insertable = false, updatable = false)
  private FaTimeConfig faTimeConfig;

  @Column
  private Integer faTimeConfigId;

  @Basic
  @Column
  private Integer facilityId;

  @Column
  private Integer stt;

  @Column
  private String img;

  @Column
  private String name;

  @Column
  private LocalTime startTime;

  @Column
  private LocalTime endTime;

  @Column(length = 4000)
  private String description;

  @Column
  private Double price;

  @ManyToOne
  @JoinColumn(name = "facilityId", insertable = false, updatable = false)
  private Facility facility;
}
