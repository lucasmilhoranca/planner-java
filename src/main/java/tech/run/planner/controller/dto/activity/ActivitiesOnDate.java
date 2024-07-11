package tech.run.planner.controller.dto.activity;

import java.time.LocalDateTime;

public record ActivitiesOnDate(String id, String title, LocalDateTime occursAt) {
}
