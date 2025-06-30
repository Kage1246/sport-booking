package mdd.com.baseapp.repository;

import mdd.com.baseapp.domain.GenerateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerateLogRepository extends JpaRepository<GenerateLog, Long> {
}
