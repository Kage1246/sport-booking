package mdd.com.baseapp.service.impl;

import jakarta.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.FaTimeConfig;
import mdd.com.baseapp.domain.FaTimeConfigDetail;
import mdd.com.baseapp.dto.FaTimeConfigDetailDto;
import mdd.com.baseapp.dto.FaTimeConfigDto;
import mdd.com.baseapp.dto.base.PageDto;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.mapper.FaTimeConfigDetailMapper;
import mdd.com.baseapp.mapper.FaTimeConfigMapper;
import mdd.com.baseapp.repository.FaTimeConfigDetailRepository;
import mdd.com.baseapp.repository.FaTimeConfigRepository;
import mdd.com.baseapp.service.interfaces.FaTimeConfigService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FaTimeConfigServiceImpl implements FaTimeConfigService {
  private final FaTimeConfigRepository faTimeConfigRepository;
  private final FaTimeConfigMapper faTimeConfigMapper;
  private final FaTimeConfigDetailRepository faTimeConfigDetailRepository;
  private final FaTimeConfigDetailMapper faTimeConfigDetailMapper;

  public FaTimeConfigServiceImpl(
      FaTimeConfigRepository faTimeConfigRepository,
      FaTimeConfigMapper faTimeConfigMapper,
      FaTimeConfigDetailRepository faTimeConfigDetailRepository,
      FaTimeConfigDetailMapper faTimeConfigDetailMapper
  ) {
    this.faTimeConfigRepository = faTimeConfigRepository;
    this.faTimeConfigMapper = faTimeConfigMapper;
    this.faTimeConfigDetailRepository = faTimeConfigDetailRepository;
    this.faTimeConfigDetailMapper = faTimeConfigDetailMapper;
  }


  @Override
  public PageDto page(PageFilterInput<FaTimeConfigDto> pageFilterInput) {
    Page<FaTimeConfig> page = faTimeConfigRepository.page(pageFilterInput);
    return PageDto.builder().contents(faTimeConfigMapper.toResponse(page.getContent()))
        .total(page.getTotalElements()).build();
  }

  /**
   * Get time config with list config detail.
   *
   * @param id time config id
   * @return config dto
   */
  @Override
  public FaTimeConfigDto getById(Integer id) {
    FaTimeConfig faTimeConfig = faTimeConfigRepository.findById(id).orElse(null);
    if (faTimeConfig == null) {
      throw new CustomException("Không tìm thấy cấu hình.");
    }
    FaTimeConfigDto result = faTimeConfigMapper.toResponse(faTimeConfig);
    List<FaTimeConfigDetailDto> configDetailDtoS = faTimeConfigDetailMapper.toResponse(
        faTimeConfigDetailRepository.findByFaTimeConfigIdAndIsDeleteFalseOrderByDayAscSttAsc(id));
    result.setConfigDetails(new LinkedList<>(configDetailDtoS));
    return result;
  }

  /**
   * Create.
   *
   * @param faTimeConfigDto config dto contains list config detail dto
   * @return saved config dto
   */
  @Transactional
  @Override
  public FaTimeConfigDto create(FaTimeConfigDto faTimeConfigDto) {
    FaTimeConfig savedFaTimeConfig =
        faTimeConfigRepository.save(faTimeConfigMapper.toEntity(faTimeConfigDto));
    log.info("Config with id {} created.", savedFaTimeConfig.getId());

    LinkedList<FaTimeConfigDetail> faTimeConfigDetails = new LinkedList<>();
    for (FaTimeConfigDetailDto detailDto : faTimeConfigDto.getConfigDetails()) {
      FaTimeConfigDetail detailEntity = faTimeConfigDetailMapper.toEntity(detailDto);
      detailEntity.setFaTimeConfigId(savedFaTimeConfig.getId());
      faTimeConfigDetails.add(detailEntity);
    }
    List<FaTimeConfigDetail> savedConfigDetails =
        faTimeConfigDetailRepository.saveAll(faTimeConfigDetails);
    log.info("List config detail of config with id {} created.", savedFaTimeConfig.getId());

    FaTimeConfigDto result = faTimeConfigMapper.toResponse(savedFaTimeConfig);
    List<FaTimeConfigDetailDto> detailDtos =
        faTimeConfigDetailMapper.toResponse(savedConfigDetails);
    result.setConfigDetails(new LinkedList<>(detailDtos));
    return result;
  }

  /**
   * Update time config with list config detail.
   *
   * @param id              faTimeConfigId
   * @param faTimeConfigDto config dto contains list config detail dto
   * @return saved config dto
   */
  @Transactional
  @Override
  public FaTimeConfigDto update(Integer id, FaTimeConfigDto faTimeConfigDto) {
    FaTimeConfig currentConfig = faTimeConfigRepository.findById(id).orElse(null);
    if (currentConfig == null) {
      log.error("Config with id {} not found.", id);
      throw new CustomException("Không tìm thấy cấu hình.");
    }
    faTimeConfigMapper.updateEntity(faTimeConfigDto, currentConfig);
    log.info("Config with id {} updated.", id);

    Map<Integer, FaTimeConfigDetail> currentConfigDetailMap =
        faTimeConfigDetailRepository.findByFaTimeConfigIdAndIsDeleteFalse(id)
            .stream().collect(Collectors.toMap(FaTimeConfigDetail::getId, Function.identity()));

    LinkedList<FaTimeConfigDetail> faTimeConfigDetails = new LinkedList<>();
    for (FaTimeConfigDetailDto detailDto : faTimeConfigDto.getConfigDetails()) {
      FaTimeConfigDetail detailEntity;
      if (detailDto.getId() == null || currentConfigDetailMap.get(detailDto.getId()) == null) {
        detailEntity = faTimeConfigDetailMapper.toEntity(detailDto);
        log.info("Created config detail for config with id {}.", id);
      } else {
        detailEntity = currentConfigDetailMap.remove(detailDto.getId());
        faTimeConfigDetailMapper.updateEntity(detailDto, detailEntity);
        log.info("Updated config detail for config with id {}.", id);
      }
      detailEntity.setFaTimeConfigId(id);
      faTimeConfigDetails.add(detailEntity);
    }
    List<FaTimeConfigDetailDto> detailDtos = faTimeConfigDetailMapper.toResponse(
        faTimeConfigDetailRepository.saveAll(faTimeConfigDetails));
    log.info("List config detail of config with id {} updated.", id);

    FaTimeConfig savedConfig = faTimeConfigRepository.save(currentConfig);
    FaTimeConfigDto result = faTimeConfigMapper.toResponse(savedConfig);
    result.setConfigDetails(new LinkedList<>(detailDtos));
    return result;
  }

  @Override
  public void deleteById(Integer id) {
    FaTimeConfig currentConfig = faTimeConfigRepository.findById(id).orElse(null);
    if (currentConfig == null) {
      log.error("Config with id {} not found.", id);
      throw new CustomException("Không tìm thấy cấu hình.");
    }
    currentConfig.setIsDelete(true);
    faTimeConfigRepository.save(currentConfig);
    log.info("Config with id {} deleted.", id);

    List<FaTimeConfigDetail> faTimeConfigDetails =
        faTimeConfigDetailRepository.findByFaTimeConfigIdAndIsDeleteFalse(id);
    for (FaTimeConfigDetail detail : faTimeConfigDetails) {
      detail.setIsDelete(true);
    }
    faTimeConfigDetailRepository.saveAll(faTimeConfigDetails);
    log.info("List config detail of config with id {} deleted.", id);
  }
}
