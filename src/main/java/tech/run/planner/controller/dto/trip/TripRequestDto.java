package tech.run.planner.controller.dto.trip;

import tech.run.planner.entity.Trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record TripRequestDto(String destination, String starts_at, String ends_at, List<String> emails_to_invite, String owner_email, String owner_name ) {

    public Trip toTrip() {
        return new Trip(
                destination,
                LocalDateTime.parse(starts_at, DateTimeFormatter.ISO_DATE_TIME),
                LocalDateTime.parse(ends_at, DateTimeFormatter.ISO_DATE_TIME),
                false,
                owner_name,
                owner_email
        );
    }
}
