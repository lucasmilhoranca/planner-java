package tech.run.planner.controller.dto.activity;

import tech.run.planner.entity.Activity;
import tech.run.planner.entity.Trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ActivityRequestDto(String title, String occursAt) {

    public Activity toActivity(Trip trip) {
        return new Activity(
                title,
                LocalDateTime.parse(occursAt, DateTimeFormatter.ISO_DATE_TIME),
                trip
        );
    }
}
