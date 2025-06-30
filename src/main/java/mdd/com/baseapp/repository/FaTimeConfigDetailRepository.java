package mdd.com.baseapp.repository;

import java.util.List;
import mdd.com.baseapp.domain.FaTimeConfigDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaTimeConfigDetailRepository extends JpaRepository<FaTimeConfigDetail, Integer> {
  List<FaTimeConfigDetail> findByFaTimeConfigIdAndIsDeleteFalse(Integer id);

  List<FaTimeConfigDetail> findByFaTimeConfigIdAndIsDeleteFalseOrderByDayAscSttAsc(Integer id);
}