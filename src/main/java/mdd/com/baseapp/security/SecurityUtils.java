package mdd.com.baseapp.security;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

  private SecurityUtils() {
  }

  /**
   * Get the login of the current user.
   *
   * @return the login of the current user.
   */
  public static Optional<String> getCurrentUsername() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
  }

  public static CustomUserDetail getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      throw new UnauthorizedException();
    }

    Object principal = auth.getPrincipal();
    if (principal instanceof CustomUserDetail) {
      return (CustomUserDetail) principal;
    }
    throw new UnauthorizedException();
  }

  public static Integer getCurrentUserId() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if (authentication == null) {
      return null;
    }
    CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
    return userDetails.getId();
  }

  private static String extractPrincipal(Authentication authentication) {
    if (authentication == null) {
      return null;
    } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
      return springSecurityUser.getUsername();
    } else if (authentication.getPrincipal() instanceof String) {
      return (String) authentication.getPrincipal();
    }
    return null;
  }

  /**
   * Get the JWT of the current user.
   *
   * @return the JWT of the current user.
   */
  public static Optional<String> getCurrentUserJWT() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .filter(authentication -> authentication.getCredentials() instanceof String)
        .map(authentication -> (String) authentication.getCredentials());
  }

  /**
   * Check if a user is authenticated.
   *
   * @return true if the user is authenticated, false otherwise.
   */
  public static boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null &&
        getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
  }

  /**
   * Checks if the current user has any of the authorities.
   *
   * @param authorities the authorities to check.
   * @return true if the current user has any of the authorities, false otherwise.
   */
  public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (authentication != null && getAuthorities(authentication).anyMatch(
        authority -> Arrays.asList(authorities).contains(authority)));
  }

  /**
   * Checks if the current user has none of the authorities.
   *
   * @param authorities the authorities to check.
   * @return true if the current user has none of the authorities, false otherwise.
   */
  public static boolean hasCurrentUserNoneOfAuthorities(String... authorities) {
    return !hasCurrentUserAnyOfAuthorities(authorities);
  }

  /**
   * Checks if the current user has a specific authority.
   *
   * @param authority the authority to check.
   * @return true if the current user has the authority, false otherwise.
   */
  public static boolean hasCurrentUserThisAuthority(String authority) {
    return hasCurrentUserAnyOfAuthorities(authority);
  }

  public static Stream<String> getAuthorities(Authentication authentication) {
    return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
  }

  public static Set<String> getPermissions() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context == null) {
      return Set.of();
    }

    Authentication authentication = context.getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Set.of();
    }

    Object principal = authentication.getPrincipal();
    if (!(principal instanceof CustomUserDetail user)) {
      return Set.of();
    }

    return user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());
  }

  public static boolean isNotAdmin() {
    Set<String> permissions = SecurityUtils.getPermissions();
    return !permissions.contains(Constant.Permission.ADMIN);
  }
}
