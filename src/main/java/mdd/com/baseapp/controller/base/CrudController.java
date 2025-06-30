package mdd.com.baseapp.controller.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mdd.com.baseapp.common.Constant;
import mdd.com.baseapp.common.constant.HttpStatusCode;
import mdd.com.baseapp.dto.base.PageParam;
import mdd.com.baseapp.dto.common.ResponseDto;
import mdd.com.baseapp.service.base.CrudService;
import mdd.com.baseapp.service.base.UtilBeanAutowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings("ALL")
@Slf4j
public abstract class CrudController<ID, P extends PageParam, REQ, S extends CrudService>
    extends UtilBeanAutowire {

  @Autowired
  protected S service;

  public CrudController() {
  }

  @GetMapping("/")
  @SneakyThrows
  public ResponseEntity<Object> paging(HttpServletRequest request, @Valid P param) {

    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .data(service.page(param))
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .build());
  }

  @GetMapping("/{id}")
  @SneakyThrows
  public ResponseEntity<Object> getById(HttpServletRequest request, @PathVariable ID id) {

    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .data(service.getById(id))
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .build());
  }

  @GetMapping("/{id}/active")
  @SneakyThrows
  public ResponseEntity<Object> getByActiveId(HttpServletRequest request, @PathVariable ID id) {

    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .data(service.getActiveById(id))
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .build());
  }

  @GetMapping("/list")
  @SneakyThrows
  public ResponseEntity<Object> getAll(HttpServletRequest request) {

    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .data(service.getAll())
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .build());
  }

  @GetMapping("/list/active")
  @SneakyThrows
  public ResponseEntity<Object> getAllActive(HttpServletRequest request) {

    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .data(service.getAllActive())
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .build());
  }

  @PostMapping("/")
  @SneakyThrows
  public ResponseEntity<Object> create(HttpServletRequest request, @RequestBody @Valid REQ body) {

    Object e = service.create(body);


    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data(e)
                .build());
  }

  @PutMapping("/{id}")
  @SneakyThrows
  public ResponseEntity<Object> update(HttpServletRequest request, @PathVariable ID id,
                                       @RequestBody @Valid REQ body) {

    Object e = service.update(id, body);

    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .data(e)
                .build());
  }

  @DeleteMapping("/{id}")
  @SneakyThrows
  public ResponseEntity<Object> delete(HttpServletRequest request, @PathVariable ID id) {

    service.delete(id);

    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .build());
  }

  @PatchMapping("/{id}/change/active")
  @SneakyThrows
  public ResponseEntity<Object> changeActive(HttpServletRequest request, @PathVariable ID id) {

    service.changeActive(id);

    return ResponseEntity.ok()
        .body(
            ResponseDto.builder()
                .code(HttpStatusCode.OK)
                .message(Constant.Response.Message.OK)
                .build());
  }
}
