package tech.run.planner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.run.planner.controller.dto.activity.ActivitiesTripResponseDto;
import tech.run.planner.controller.dto.activity.ActivityRequestDto;
import tech.run.planner.controller.dto.activity.ActivityResponseDto;
import tech.run.planner.controller.dto.link.LinkRequestDto;
import tech.run.planner.controller.dto.link.LinkResponseDto;
import tech.run.planner.controller.dto.link.LinksTripResponseDto;
import tech.run.planner.controller.dto.participant.ParticipantInviteDto;
import tech.run.planner.controller.dto.participant.ParticipantsTripResponseDto;
import tech.run.planner.controller.dto.trip.TripRequestDto;
import tech.run.planner.controller.dto.trip.TripRequestUpdateDto;
import tech.run.planner.controller.dto.trip.TripResponseDto;
import tech.run.planner.entity.Trip;
import tech.run.planner.service.LinkService;
import tech.run.planner.service.TripService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponseDto> createTrip(@RequestBody TripRequestDto payloadDto) {
        var newTripId = tripService.createTrip(payloadDto);

        return new ResponseEntity<>(new TripResponseDto(UUID.fromString(newTripId)), HttpStatus.CREATED);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getById(@PathVariable UUID tripId) {
        var trip = tripService.getById(tripId);

        return ResponseEntity.ok(trip);
    }

    @GetMapping("/{tripId}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID tripId) {
        tripService.confirmTrip(tripId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Void> updateTrip(@PathVariable UUID tripId, @RequestBody TripRequestUpdateDto updateDto) {
        tripService.updateTrip(tripId, updateDto);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tripId}/invites")
    public ResponseEntity<Void> inviteParticipant(@PathVariable UUID tripId, @RequestBody ParticipantInviteDto participantInviteDto) {
        tripService.inviteParticipant(tripId, participantInviteDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{tripId}/participants")
    public ResponseEntity<List<ParticipantsTripResponseDto>> getAllParticipants(@PathVariable UUID tripId) {
        var participants = tripService.getAllParticipants(tripId);

        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{tripId}/activities")
    public ResponseEntity<ActivityResponseDto> registerActivity(@PathVariable UUID tripId, @RequestBody ActivityRequestDto activityRequestDto) {
        var activity = tripService.createTripActivity(tripId, activityRequestDto);

        return new ResponseEntity<ActivityResponseDto>(activity, HttpStatus.CREATED);
    }

    @GetMapping("/{tripId}/activities")
    public ResponseEntity<List<ActivitiesTripResponseDto>> getAllActivities(@PathVariable UUID tripId) {
        var activities = tripService.getAllActivities(tripId);

        return ResponseEntity.ok(activities);
    }

    @PostMapping("/{tripId}/links")
    public ResponseEntity<LinkResponseDto> registerLink(@PathVariable UUID tripId, @RequestBody LinkRequestDto linkRequestDto) {
        var link = tripService.createTripLink(tripId, linkRequestDto);

        return new ResponseEntity<LinkResponseDto>(link, HttpStatus.CREATED);
    }

    @GetMapping("/{tripId}/links")
    public ResponseEntity<List<LinksTripResponseDto>> getAllLinks(@PathVariable UUID tripId) {
        var links = tripService.getAllLinks(tripId);

        return ResponseEntity.ok(links);
    }
}
