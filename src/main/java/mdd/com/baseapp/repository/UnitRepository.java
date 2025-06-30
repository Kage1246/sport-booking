package mdd.com.baseapp.repository;

import java.util.Optional;
import mdd.com.baseapp.domain.Unit;
import mdd.com.baseapp.repository.custom.UnitRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer>, UnitRepositoryCustom {
  Optional<Unit> findByIdAndIsDeleteFalse(Integer id);

  Optional<Unit> findByIdAndFacilityIdAndIsDeleteFalse(Integer id, Integer facilityId);
}
