package mdd.com.baseapp.repository;

import mdd.com.baseapp.domain.Notify;
import mdd.com.baseapp.repository.custom.CustomJpa;
import mdd.com.baseapp.repository.custom.NotifyRepositoryCustom;

public interface NotifyRepository extends CustomJpa<Notify, Integer>, NotifyRepositoryCustom {
}
