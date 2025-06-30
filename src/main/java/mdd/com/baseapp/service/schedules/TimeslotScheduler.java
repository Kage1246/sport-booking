package mdd.com.baseapp.service.schedules;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import mdd.com.baseapp.common.SystemConfig;
import mdd.com.baseapp.domain.FaTimeConfigDetail;
import mdd.com.baseapp.domain.GenerateLog;
import mdd.com.baseapp.domain.Timeslot;
import mdd.com.baseapp.domain.Unit;
import mdd.com.baseapp.repository.FaTimeConfigDetailRepository;
import mdd.com.baseapp.repository.GenerateLogRepository;
import mdd.com.baseapp.repository.SystemConfigRepository;
import mdd.com.baseapp.repository.TimeslotRepository;
import mdd.com.baseapp.repository.UnitRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Log4j2
@Service
public class TimeslotScheduler {
  private final SystemConfigRepository systemConfigRepository;
  private final UnitRepository unitRepository;
  private final FaTimeConfigDetailRepository faTimeConfigDetailRepository;
  private final TimeslotRepository timeslotRepository;
  private final GenerateLogRepository generateLogRepository;

  public TimeslotScheduler(
      SystemConfigRepository systemConfigRepository,
      UnitRepository unitRepository,
      FaTimeConfigDetailRepository faTimeConfigDetailRepository,
      TimeslotRepository timeslotRepository,
      GenerateLogRepository generateLogRepository
  ) {
    this.systemConfigRepository = systemConfigRepository;
    this.unitRepository = unitRepository;
    this.faTimeConfigDetailRepository = faTimeConfigDetailRepository;
    this.timeslotRepository = timeslotRepository;
    this.generateLogRepository = generateLogRepository;
  }

  private int getDayInCycle(Integer period, LocalDate date) {
    if (period != null && period == 1) {
      return date.getDayOfWeek().getValue(); // Monday = 1, Sunday = 7
    } else {
      return 1; // every day
    }
  }

  @Scheduled(cron = "0 0 2 * * *")
  @Transactional
  public void generateTimeslots() {
    // Get start and end day to generate
    Long generateDays = systemConfigRepository.getLong(SystemConfig.TIMESLOT_GENERATE_DAYS);
    LocalDate today = LocalDate.now();
    LocalDate endDate = today.plusDays(generateDays != null ? generateDays : 7);
    log.info("Starting generate timeslots from {} to {}", today, endDate);

    // Get all units
    List<Unit> units = unitRepository.findAll();
    for (Unit unit : units) {
      log.info("Generating timeslots for unit={}; unitId={}; facilityId={}", unit.getName(), unit.getId(), unit.getFacilityId());
      Integer faTimeConfigId = unit.getFaTimeConfigId();
      if (faTimeConfigId == null) {
        log.warn("Unit {} has no faTimeConfigId, skip", unit.getName());
        continue;
      }

      // Get all timeslots of unit group by day
      Map<Integer, List<FaTimeConfigDetail>> configDetailMap = faTimeConfigDetailRepository
          .findByFaTimeConfigIdAndIsDeleteFalseOrderByDayAscSttAsc(faTimeConfigId)
          .stream()
          .collect(Collectors.groupingBy(FaTimeConfigDetail::getDay));

      Map<LocalDate, List<Timeslot>> timeslotMap = timeslotRepository
          .findByUnitIdAndIsDeleteFalse(unit.getId())
          .stream()
          .collect(Collectors.groupingBy(Timeslot::getDay));

      // Generate timeslots
      List<Timeslot> timeslots = new ArrayList<>();
      List<GenerateLog> generateLogs = new ArrayList<>();
      for (LocalDate date = today; !date.isAfter(endDate); date = date.plusDays(1)) {
        if (timeslotMap.get(date) != null && !timeslotMap.get(date).isEmpty()) {
          // If timeslot exists, skip
          continue;
        }

        // get day in cycle
        int dayInCycle = getDayInCycle(unit.getFaTimeConfig().getPeriod(), date);

        // Get timeslots from config detail
        List<FaTimeConfigDetail> details = configDetailMap.get(dayInCycle);

        if (!CollectionUtils.isEmpty(details)) {
          for (FaTimeConfigDetail detail : details) {
            Timeslot slot = Timeslot.builder()
                .unitId(unit.getId())
                .faTimeConfigDetailId(detail.getId())
                .day(date)
                .startTime(detail.getStartTime())
                .endTime(detail.getEndTime())
                .stt(detail.getStt())
                .status(detail.getStatus())
                .build();

            timeslots.add(slot);
          }
        }

        generateLogs.add(
            GenerateLog.builder()
                .createdAt(LocalDateTime.now())
                .unitId(unit.getId())
                .faTimeConfigId(faTimeConfigId)
                .generatedDate(date)
                .timeslotQuantity(timeslots.size())
                .build()
        );
      }
      timeslotRepository.saveAll(timeslots);
      generateLogRepository.saveAll(generateLogs);
      log.info("Generated {} timeslots for unit={}; unitId={}; facilityId={}", timeslots.size(), unit.getName(), unit.getId(), unit.getFacilityId());
    }
  }
}
