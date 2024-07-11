package tech.run.planner.controller.dto.activity;

import java.time.LocalDateTime;
import java.util.List;

public record ActivitiesTripResponseDto(LocalDateTime date, List<ActivitiesOnDate> activities) {
}
