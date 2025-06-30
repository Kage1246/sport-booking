package mdd.com.baseapp.repository;

import java.util.Optional;
import mdd.com.baseapp.domain.FaTimeConfig;
import mdd.com.baseapp.repository.custom.FaTimeConfigRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaTimeConfigRepository
    extends JpaRepository<FaTimeConfig, Integer>, FaTimeConfigRepositoryCustom {
  Optional<FaTimeConfig> findByIdAndIsDeleteFalse(Integer faTimeConfigId);

  Optional<FaTimeConfig> findByIdAndFacilityIdAndIsDeleteFalse(Integer id, Integer facilityId);
}
