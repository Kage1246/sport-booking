package mdd.com.baseapp.service.direct;

import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.AllArgsConstructor;
import mdd.com.baseapp.domain.TransactionE;
import mdd.com.baseapp.domain.User;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.repository.TransactionRepository;
import mdd.com.baseapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final UserRepository userRepository;

  TransactionE createTransaction(Integer userId, Double amount, String tranCode) {
    TransactionE transactionE = new TransactionE();
    transactionE.setUserId(userId);
    transactionE.setAmount(amount);
    transactionE.setStatus(2);
    transactionE.setTranCode(tranCode);
    return transactionRepository.save(transactionE);
  }

  @Transactional
  Boolean updateTransaction(String tranCode, Integer status) {
    TransactionE transactionE = transactionRepository.findByTranCode(tranCode).orElseThrow(
        () -> new CustomException("Transaction not found")
    );
    if (!Objects.equals(transactionE.getStatus(), status)) {
      transactionE.setStatus(status);
      if (status == 1) { // thành cong
        User user = userRepository.findById(transactionE.getUserId()).orElseThrow(
            () -> new CustomException("User not found")
        );
        user.setBalance(user.getBalance() + transactionE.getAmount());
        userRepository.save(user);
      }
      transactionRepository.save(transactionE);
    }
    return true;
  }
}
