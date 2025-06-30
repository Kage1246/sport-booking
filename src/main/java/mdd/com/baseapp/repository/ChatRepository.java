package mdd.com.baseapp.repository;

import mdd.com.baseapp.domain.Chat;
import mdd.com.baseapp.repository.custom.ChatRepositoryCustom;
import mdd.com.baseapp.repository.custom.CustomJpa;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CustomJpa<Chat, Integer>, ChatRepositoryCustom {
}
