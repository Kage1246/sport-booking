package mdd.com.baseapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.common.util.ValidateCustomUtil;
import mdd.com.baseapp.dto.account.LoginDto;
import mdd.com.baseapp.dto.account.ProfileDto;
import mdd.com.baseapp.dto.account.RegisterDto;
import mdd.com.baseapp.dto.common.ResponseDto;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.service.direct.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class AccountController {

  private final PasswordEncoder passwordEncoder;
  private final AccountService accountService;
  private final ObjectMapper objectMapper;
  private final Validator validator;
  @Value("${app.pathImage}")
  public String baseUrlImage;

  public AccountController(PasswordEncoder passwordEncoder1, AccountService accountService,
                           ObjectMapper objectMapper, Validator validator) {
    this.passwordEncoder = passwordEncoder1;
    this.accountService = accountService;
    this.objectMapper = objectMapper;
    this.validator = validator;
  }

  @PostMapping("/account/authenticate")
  public ResponseEntity<ResponseDto> authorize(@Valid @RequestBody LoginDto loginDto) {
    return ResponseEntity.ok().body(
        ResponseDto.builder()
            .data(accountService.authorize(loginDto))
            .code(HttpStatusCode.OK)
            .message("Đăng nhập thành công")
            .build()
    );
  }

  @PostMapping("/account/register")
  public ResponseEntity<ResponseDto> register(@RequestBody RegisterDto registerDto) {
    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data(accountService.register(registerDto))
                .build());
  }

  @SneakyThrows
  @GetMapping("/account/getProfile")
  public ResponseEntity<ResponseDto> getProfile() {
    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data(accountService.getProfile())
                .build());
  }

  @SneakyThrows
  @PostMapping("/account/editProfile")
  public ResponseEntity<ResponseDto> editProfile(@RequestBody ProfileDto dto) {
    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data(accountService.editProfile(dto))
                .build());
  }

  @SneakyThrows
  @PostMapping("/account/editProfileWithImg")
  public ResponseEntity<ResponseDto> editProfileWithImg(
      @RequestParam(value = "file", required = false) MultipartFile file,
      String profileDto) {
    ProfileDto dto = this.objectMapper.readValue(profileDto, ProfileDto.class);
    ValidateCustomUtil<ProfileDto> validateCustomUtil = new ValidateCustomUtil<>(validator);
    String error = validateCustomUtil.validate(dto);
    if (!StringUtils.isEmpty(error)) {
      throw new CustomException(error);
    }
    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data(accountService.editProfileWithImg(file, dto))
                .build());
  }

  @PostMapping("/public/createPassword")
  public ResponseEntity<String> createPassword(@RequestBody String pass) {
    return new ResponseEntity<>(this.passwordEncoder.encode(pass), HttpStatus.OK);
  }

  @GetMapping("/public/images/{imageName:.+}")
  public ResponseEntity<Resource> getImage(@PathVariable String imageName)
      throws MalformedURLException {
    imageName = this.baseUrlImage + imageName;
    Path imagePath = Paths.get(imageName);
    Resource imageResource = new UrlResource(imagePath.toUri());

    // Kiểm tra xem tập tin có tồn tại không
    if (imageResource.exists() || imageResource.isReadable()) {
      return ResponseEntity.ok()
          .contentType(MediaType.IMAGE_JPEG) // Đặt loại nội dung cho ảnh
          // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageResource.getFilename() + "\"")
          .body(imageResource);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
