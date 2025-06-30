package mdd.com.baseapp.repository;

import mdd.com.baseapp.domain.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Integer> {
  @Query("select coalesce(s.longValue, null) from SystemConfig s where s.key = ?1 and s.type = 'LONG'")
  Long getLong(String key);

  @Query("select coalesce(s.doubleValue, null) from SystemConfig s where s.key = ?1 and s.type = 'DOUBLE'")
  Double getDouble(String key);

  @Query("select coalesce(s.textValue, null) from SystemConfig s where s.key = ?1 and s.type = 'TEXT'")
  String getText(String key);

  @Query("select coalesce(s.booleanValue, null) from SystemConfig s where s.key = ?1 and s.type = 'BOOLEAN'")
  Boolean getBoolean(String key);
}
