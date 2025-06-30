package mdd.com.baseapp.security;

import java.util.Locale;
import java.util.Optional;
import mdd.com.baseapp.domain.User;
import mdd.com.baseapp.exception.UserNameNotFoundExceptionCustom;
import mdd.com.baseapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

  private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

  private final UserRepository userRepository;

  public DomainUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(final String login) {
    log.debug("Authenticating {}", login);

    String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
    Optional<User> userOptional = userRepository.findOneByUsernameActive(lowercaseLogin);
    if (userOptional.isPresent()) {
      return createSpringSecurityUser(userOptional.get());
    } else {
      throw new UserNameNotFoundExceptionCustom();
    }
  }

  private CustomUserDetail createSpringSecurityUser(User user) {
    return new CustomUserDetail(user, userRepository);
  }

}
