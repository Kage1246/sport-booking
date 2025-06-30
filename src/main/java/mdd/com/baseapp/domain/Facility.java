package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mdd.com.baseapp.common.util.StringUtil;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facility", indexes = {
    @Index(name = "idx_facility_search", columnList = "searchText")})
public class Facility extends AbstractAuditingEntity implements Serializable {
  @Column(nullable = false)
  private String name;
  @Column(length = 500)
  private String address;
  @Column
  private String phoneNumber;
  @Column(length = 4000)
  private String description;
  @Column
  private String owner;
  @Column
  private String img;
  @Column
  private LocalTime startTime;
  @Column
  private LocalTime endTime;
  @Column(length = 1024)
  private String searchText;

  @PrePersist
  @PreUpdate
  public void generateSearchText() {
    this.searchText =
        Stream.of(name, address, phoneNumber).filter(Objects::nonNull).map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(StringUtil::removeAccent)                       // remove dấu
            .map(s -> s.replaceAll("[^\\p{L}\\p{Nd}\\s]", "")) // bỏ ký tự đặc biệt
            .collect(Collectors.joining(" ")).toLowerCase();
  }
}
