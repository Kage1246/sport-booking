package mdd.com.baseapp.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import mdd.com.baseapp.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

  Optional<Otp> findFirstByUserIdAndTypeAndStatus(Integer userId, Integer type, Boolean status);

  @Modifying
  @Transactional
  @Query(value = "update OTP set status = false,is_active=0 where user_id = ?1 and type = ?2 and status = true", nativeQuery = true)
  void resetOtp(Integer userId, Integer type);

}
