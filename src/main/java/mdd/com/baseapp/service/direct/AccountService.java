package mdd.com.baseapp.service.direct;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import mdd.com.baseapp.domain.User;
import mdd.com.baseapp.domain.UserRole;
import mdd.com.baseapp.dto.account.LoginDto;
import mdd.com.baseapp.dto.account.ProfileDto;
import mdd.com.baseapp.dto.account.RegisterDto;
import mdd.com.baseapp.dto.account.UserDto;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.security.SecurityUtils;
import mdd.com.baseapp.service.base.BaseAutowire;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AccountService extends BaseAutowire {

  @SneakyThrows
  @Transactional
  public UserDto register(RegisterDto dto) { //TODO: đoạn này chưa check quyền trước khi tạo
    List<User> userList = userRepository.findAllByUsername(dto.getUsername());
    if (!userList.isEmpty()) {
      throw new CustomException("Tài khoản đã tồn tại");
    }
    User user = modelMapper.map(dto, User.class);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setBalance(0D);
    user.setStatus(2);
    userRepository.save(user);
    List<UserRole> userRoleList = new ArrayList<>();
    if (dto.getRoleIds() != null) {
      for (Integer roleId : dto.getRoleIds()) {
        userRoleList.add(new UserRole(user.getId(), roleId));
      }
    }
    userRoleRepository.saveAll(userRoleList);

    return authorize(new LoginDto(dto.getUsername(), dto.getPassword()));
  }

  public UserDto authorize(LoginDto loginDto) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            loginDto.getUsername(),
            loginDto.getPassword()
        );

    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    User user =
        userRepository.findOneByUsername(loginDto.getUsername()).orElseThrow();
    UserDto userDto = modelMapper.map(user, UserDto.class);
    userDto.setPermisssion(SecurityUtils.getPermissions());
    userDto.setJwt(tokenProvider.generateToken(authentication));
    return userDto;
  }

  @SneakyThrows
  public UserDto editProfile(ProfileDto dto) {
    dto.setAvatarUrl(fileUtil.saveFrom3rd(dto.getAvatarUrl()));
    return editProfileGeneral(dto);
  }

  @SneakyThrows
  public UserDto editProfileWithImg(MultipartFile file, ProfileDto dto) {
    if (file != null) {
      dto.setAvatarUrl(fileUtil.saveFile(file));
    }
    return editProfileGeneral(dto);
  }

  private UserDto editProfileGeneral(ProfileDto dto) {
    List<User> userList = userRepository.findAllByUsername(
        SecurityContextHolder.getContext().getAuthentication().getName());
    if (userList == null || userList.isEmpty()) {
      throw new CustomException("Account not exist");
    }
    User user = userList.get(0);
    if (!Strings.isEmpty(dto.getAvatarUrl())) {
      user.setAvatarUrl(dto.getAvatarUrl());
    }
    user.setFullname(dto.getFullname());
    user.setAccountNumber(dto.getAccountNumber());
    user.setGmail(dto.getGmail());
    user.setGender(dto.getGender());
    userRepository.save(user);
    return modelMapper.map(user, UserDto.class);
  }

  @SneakyThrows
  public Object getProfile() {
    List<User> userList = userRepository.findAllByUsername(
        SecurityContextHolder.getContext().getAuthentication().getName());
    if (userList == null || userList.isEmpty()) {
      throw new CustomException("Account not exist");
    }
    User user = userList.get(0);
    return modelMapper.map(user, ProfileDto.class);
  }

  public User getCurrentUser() {
    Integer id = SecurityUtils.getCurrentUserId();
    if (id == null) {
      return null;
    }
    return userRepository.findById(id).orElse(null);
  }
}
