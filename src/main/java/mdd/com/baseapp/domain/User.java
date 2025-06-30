package mdd.com.baseapp.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mdd.com.baseapp.common.enumpackage.GenderEnum;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_app")
public class User extends AbstractAuditingEntity {
  @Basic
  @Column(name = "username")
  private String username;
  @Basic
  @Column(name = "fullname")
  private String fullname;
  @Basic
  @Column(name = "avatar_url")
  private String avatarUrl;
  @Basic
  @Column(name = "account_number")
  private String accountNumber;
  @Basic
  @Column(name = "gmail")
  private String gmail;
  @Basic
  @Column(name = "balance")
  private Double balance = 0d;
  @Basic
  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private GenderEnum gender = GenderEnum.MALE;
  @Basic
  @Column(name = "password")
  private String password;
  @Basic
  @Column(name = "status", nullable = false)
  private Integer status = 0;//1 là đã kích hoạt gmail, 0 là chưa kích hoạt gmail

  @Basic
  @Column
  private Integer facilityId;

  @ManyToOne
  @JoinColumn(name = "facilityId", insertable = false, updatable = false)
  private Facility facility;


  @Transient
  @JsonDeserialize
  @JsonSerialize
  private Set<String> permisssion = new HashSet<>();
}
