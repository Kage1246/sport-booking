package mdd.com.baseapp.repository;

import java.time.LocalDate;
import java.util.List;
import mdd.com.baseapp.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Integer> {
  List<Timeslot> findByUnitIdAndIsDeleteFalse(Integer unitId);
}
