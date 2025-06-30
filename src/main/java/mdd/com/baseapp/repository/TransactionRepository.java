package mdd.com.baseapp.repository;

import java.util.Optional;
import mdd.com.baseapp.domain.TransactionE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionE, Integer> {

  Optional<TransactionE> findByTranCodeAndStatus(String trancode, Integer status);

  Optional<TransactionE> findByTranCode(String trancode);
}
