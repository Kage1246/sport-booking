package mdd.com.baseapp.config;

import java.util.Optional;
import mdd.com.baseapp.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
//@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(SecurityUtils.getCurrentUsername().orElse("system"));
  }
}
