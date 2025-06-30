package mdd.com.baseapp.repository.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface CustomJpa<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

}
