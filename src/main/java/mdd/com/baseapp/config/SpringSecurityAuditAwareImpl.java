//package mdd.com.baseapp.config;
//
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Optional;
//
//public class SpringSecurityAuditAwareImpl implements AuditorAware<String> {
//
//    @Override
//    public Optional<String> getCurrentAuditor() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null ||
//                !authentication.isAuthenticated() ||
//                authentication instanceof AnonymousAuthenticationToken) {
//
//            return Optional.empty();
//        }
//
//        final Object o = authentication.getPrincipal();
//
//        if (o instanceof UserDetails userDetails) {
//
//            return Optional.ofNullable(userDetails.getUsername());
//        }
//
//        return Optional.empty();
//    }
//}
//
