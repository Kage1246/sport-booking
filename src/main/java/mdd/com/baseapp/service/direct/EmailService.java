package mdd.com.baseapp.service.direct;

import java.util.Objects;
import java.util.Random;
import mdd.com.baseapp.domain.Otp;
import mdd.com.baseapp.domain.User;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.repository.OtpRepository;
import mdd.com.baseapp.repository.UserRepository;
import mdd.com.baseapp.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final JavaMailSender mailSender;
  private final OtpRepository otpRepository;
  private final UserRepository userRepository;
  private final AccountService accountService;
  @Value("${app.otp.expirationInMinute}")
  private String expirationS;

  public EmailService(JavaMailSender mailSender, OtpRepository otpRepository,
                      UserRepository userRepository, AccountService accountService) {
    this.mailSender = mailSender;
    this.otpRepository = otpRepository;
    this.userRepository = userRepository;
    this.accountService = accountService;
  }

  public void sendEmailToUser() {
    Integer id = SecurityUtils.getCurrentUserId();
    if (id == null) {
      throw new CustomException("User can not be found");
    }
    User user =
        userRepository.findById(id).orElseThrow(() -> new CustomException("User can not be found"));
    //        Optional<Otp> otpOptional = otpRepository.findFirstByUserIdAndTypeAndStatus(id, 1, true);
    //        if (otpOptional.isPresent()) return;
    sendOtpEmail(user.getGmail(), generateOtp(id, 1));
  }

  public void sendOtpEmail(String toEmail, String otp) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("gnhatminhg@gmail.com");
    message.setTo(toEmail);
    message.setSubject("Shadow Middle Man - OTP for Account Activation");
    message.setText(buildEmailBody(otp));
    mailSender.send(message);
  }

  private String buildEmailBody(String otp) {
    return "Dear User,\n\n"
        + "Thank you for registering with Shadow Middle Man. "
        + "To activate your account, please use the following One-Time Password (OTP):\n\n"
        + otp + "\n\n"
        + "This OTP is valid for the next " + expirationS
        + " minutes. Please do not share this OTP with anyone.\n\n"
        + "If you did not request this email, please ignore it.\n\n"
        + "Best regards,\n"
        + "Shadow Middle Man Team";
  }

  public String generateOtp(Integer userId, Integer type) {
    this.otpRepository.resetOtp(userId, type);
    Random random = new Random();
    int otp = 100000 + random.nextInt(900000);
    String otpS = String.valueOf(otp);
    Otp otpE = new Otp(otpS, userId, type);
    this.otpRepository.save(otpE);
    return otpS;
  }

  public Integer activeAccount(String otp) {
    User user = accountService.getCurrentUser();
    if (user == null) {
      throw new CustomException("User can not be found");
    }
    if (user.getStatus() != 2) {
      throw new CustomException("User is already active email");
    }
    Otp otp1 = otpRepository.findFirstByUserIdAndTypeAndStatus(user.getId(), 1, true)
        .orElseThrow(() -> new CustomException("OTP not found"));
    if (Objects.equals(otp, otp1.getCode())) {
      user.setStatus(1);
      User user1 = userRepository.save(user);
      otp1.setStatus(false);
      otpRepository.save(otp1);
      return user1.getStatus();
    }
    throw new CustomException("OTP is not valid");
  }
}
