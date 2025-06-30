package mdd.com.baseapp.common.util;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
@Log4j2
public class FileUtil {
  @Value("${app.hostUrl}")
  String hostUrl;
  @Value("${app.pathImage}")
  private String saveUrl;

  public String saveFile(MultipartFile multipartFile)
      throws IOException {
    if (multipartFile == null || multipartFile.isEmpty()) {
      return "NOT_EXIT";
    }
    String fileName = StringUtils.cleanPath(
        multipartFile.getOriginalFilename() != null ? multipartFile.getOriginalFilename() : "");
    Path uploadPath = Paths.get(saveUrl);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    String fileCode =
        '_' + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss")) + '_'
            + randomAlphanumeric(8);
    fileName = fileCode + "-" + fileName;
    Path filePath;
    try (InputStream inputStream = multipartFile.getInputStream()) {
      filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioe) {
      throw new IOException("Could not save file: " + fileName, ioe);
    }
    //        fileName = hostUrl + "api/public/images/" + fileName;
    fileName = "api/public/images/" + fileName;
    return fileName;
  }

  public String saveFile(String urlString) {
    HttpURLConnection httpConn = null;
    InputStream inputStream = null;
    FileOutputStream outputStream = null;
    try {
      URL url = new URL(urlString);
      httpConn = (HttpURLConnection) url.openConnection();
      int responseCode = httpConn.getResponseCode();
      String fileExtension = FilenameUtils.getExtension(url.getPath());
      String fileCode =
          '_' + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss")) + '_'
              + randomAlphanumeric(8) + '.' + fileExtension;
      // Kiểm tra xem kết nối đã thành công hay không
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Mở một luồng đọc từ kết nối
        inputStream = httpConn.getInputStream();

        // Ghi dữ liệu từ luồng đầu vào vào file đích
        outputStream = new FileOutputStream(saveUrl + fileCode);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead);
        }
        return hostUrl + "api/public/images/" + fileCode;
      } else {
        log.error("Download file not success : " + urlString);
        log.error("Code = " + responseCode);
        return null;
      }
    } catch (Exception e) {
      log.error(e);
    } finally {
      try {
        if (outputStream != null) {
          outputStream.close();
        }
        if (inputStream != null) {
          inputStream.close();
        }
        if (httpConn != null) {
          httpConn.disconnect();
        }
      } catch (IOException e) {
        log.error("Error while closing resources: " + e.getMessage());
      }
    }
    return null;
  }

  @SneakyThrows
  public String saveFiles(MultipartFile[] imgs) {
    StringBuilder stringBuilder = new StringBuilder();
    for (MultipartFile img : imgs) {
      if (img != null) {
        String newFile = saveFile(img);
        if (!Strings.isEmpty(newFile)) {
          stringBuilder.append(!stringBuilder.isEmpty() ? "," : "").append(newFile);
        }
      }
    }
    return stringBuilder.toString();
  }

  public String saveFrom3rd(String urls3rd) {
    StringBuilder stringBuilder = new StringBuilder();
    List<String> list = Arrays.stream(urls3rd.split(",")).map(String::trim).toList();
    for (int i = 0; i < list.size(); i++) {
      String x = list.get(i);
      String newFile = saveFile(x);
      if (!Strings.isEmpty(newFile)) {
        stringBuilder.append(!stringBuilder.isEmpty() ? "," : "").append(newFile);
      }
    }
    return stringBuilder.toString();
  }
}