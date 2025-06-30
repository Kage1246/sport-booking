package mdd.com.baseapp.service.base;

import mdd.com.baseapp.common.util.FileUtil;
import mdd.com.baseapp.config.VnpayConfig;
import mdd.com.baseapp.mapper.FaTimeConfigDetailMapper;
import mdd.com.baseapp.mapper.FaTimeConfigMapper;
import mdd.com.baseapp.mapper.FacilityMapper;
import mdd.com.baseapp.mapper.UnitMapper;
import mdd.com.baseapp.repository.ChatRepository;
import mdd.com.baseapp.repository.FaTimeConfigDetailRepository;
import mdd.com.baseapp.repository.FaTimeConfigRepository;
import mdd.com.baseapp.repository.FacilityRepository;
import mdd.com.baseapp.repository.UnitRepository;
import mdd.com.baseapp.repository.UserRepository;
import mdd.com.baseapp.repository.UserRoleRepository;
import mdd.com.baseapp.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseAutowire extends UtilBeanAutowire {
  @Autowired
  public PasswordEncoder passwordEncoder;

  @Autowired
  public TokenProvider tokenProvider;

  @Autowired
  public AuthenticationManagerBuilder authenticationManagerBuilder;

  @Autowired
  public UserRepository userRepository;
  @Autowired
  public UserRoleRepository userRoleRepository;
  @Autowired
  public ChatRepository chatRepository;
  @Autowired
  public FileUtil fileUtil;
  @Autowired
  public VnpayConfig vnpayConfig;
  @Autowired
  public FacilityRepository facilityRepository;
  @Autowired
  public FacilityMapper facilityMapper;
  @Autowired
  public UnitRepository unitRepository;
  @Autowired
  public UnitMapper unitMapper;
  @Autowired
  public FaTimeConfigRepository faTimeConfigRepository;
  @Autowired
  public FaTimeConfigMapper faTimeConfigMapper;
  @Autowired
  public FaTimeConfigDetailRepository faTimeConfigDetailRepository;
  @Autowired
  public FaTimeConfigDetailMapper faTimeConfigDetailMapper;
}
