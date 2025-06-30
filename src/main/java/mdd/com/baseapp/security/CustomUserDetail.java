package mdd.com.baseapp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.NonNull;
import mdd.com.baseapp.domain.User;
import mdd.com.baseapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {
  private final User user;
  private final UserRepository userRepository;
  private List<GrantedAuthority> grantedAuthorities;

  public CustomUserDetail(@NonNull User user, UserRepository userRepository) {
    this.user = user;
    this.userRepository = userRepository;
  }

  public Integer getId() {
    return this.user.getId();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (grantedAuthorities == null) {
      grantedAuthorities = new ArrayList<>();
      List<String> listPermission = userRepository.findPermissionsByUserId(user.getId());
      for (String permission : listPermission) {
        grantedAuthorities.add(new SimpleGrantedAuthority(permission));
      }
    }

    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return user.getStatus() > 0;
  }

  public User getUser() {
    return user;
  }
}
