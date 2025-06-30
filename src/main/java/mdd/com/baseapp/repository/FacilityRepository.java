package mdd.com.baseapp.repository;

import java.util.Optional;
import mdd.com.baseapp.domain.Facility;
import mdd.com.baseapp.repository.custom.FacilityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository
    extends JpaRepository<Facility, Integer>, FacilityRepositoryCustom {
  Optional<Facility> findByIdAndIsDeleteFalse(Integer id);
}
