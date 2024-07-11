package tech.run.planner.service;

import org.springframework.stereotype.Service;
import tech.run.planner.controller.dto.activity.ActivitiesOnDate;
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
import tech.run.planner.entity.Activity;
import tech.run.planner.entity.Trip;
import tech.run.planner.exceptions.TripNotFoundException;
import tech.run.planner.repository.TripRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final ParticipantService participantService;
    private final ActivityService activityService;
    private final LinkService linkService;

    public TripService(TripRepository tripRepository, ParticipantService participantService, ActivityService activityService, LinkService linkService) {
        this.tripRepository = tripRepository;
        this.participantService = participantService;
        this.activityService = activityService;
        this.linkService = linkService;
    }

    public String createTrip(TripRequestDto payloadDto) {
        var newTrip = tripRepository.save(payloadDto.toTrip());

        participantService.registerParticipantsToTrip(payloadDto.emails_to_invite(), newTrip);

        return newTrip.getId().toString();
    }

    public Trip getById(UUID tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(TripNotFoundException::new);
    }

    public void updateTrip(UUID tripId, TripRequestUpdateDto dto) {
        var trip = getById(tripId);

        trip.setDestination(dto.destination());
        trip.setStartsAt(LocalDateTime.parse(dto.startsAt(), DateTimeFormatter.ISO_DATE_TIME));
        trip.setEndsAt(LocalDateTime.parse(dto.endsAt(), DateTimeFormatter.ISO_DATE_TIME));

        tripRepository.save(trip);
    }

    public void confirmTrip(UUID tripId) {
        var trip = getById(tripId);

        trip.setConfirmed(true);

        tripRepository.save(trip);

        participantService.triggerConfirmationEmailToParticipants(tripId);
    }

    public void inviteParticipant(UUID tripId, ParticipantInviteDto participantInviteDto) {
        var trip = getById(tripId);

        participantService.registerParticipantsToTrip(List.of(participantInviteDto.email()), trip);

        if(trip.getConfirmed()) participantService.triggerConfirmationEmailToParticipant(participantInviteDto.email());
    }

    public List<ParticipantsTripResponseDto> getAllParticipants(UUID tripId) {
        var participants = participantService.getAllParticipantsFromTrip(tripId);

        return participants
                .stream()
                .map(
                        participant -> new ParticipantsTripResponseDto(
                                participant.getId(),
                                participant.getName(),
                                participant.getEmail(),
                                participant.isConfirmed()
                        )
                ).toList();
    }

    public ActivityResponseDto createTripActivity(UUID tripId, ActivityRequestDto activityRequestDto) {
        var trip = getById(tripId);

        var activity = activityService.createActivity(activityRequestDto.toActivity(trip));

        return new ActivityResponseDto(activity.getId().toString());
    }

    public List<ActivitiesTripResponseDto> getAllActivities(UUID tripId) {
        var activities = activityService.getAllActivitiesFromTrip(tripId);

        Map<LocalDateTime, List<Activity>> groupedActivities = activities.stream()
                .collect(Collectors.groupingBy(activity -> activity.getOccursAt().toLocalDate().atStartOfDay()));

        return groupedActivities.entrySet().stream()
                .map(this::getActivitiesTripResponseDto)
                .toList();

    }

    private ActivitiesTripResponseDto getActivitiesTripResponseDto(Map.Entry<LocalDateTime, List<Activity>> entry) {
        LocalDateTime date = entry.getKey();
        List<ActivitiesOnDate> activitiesOnDateDto = getActivitiesOnDates(entry);
        return new ActivitiesTripResponseDto(date, activitiesOnDateDto);
    }

    private List<ActivitiesOnDate> getActivitiesOnDates(Map.Entry<LocalDateTime, List<Activity>> entry) {
        return entry.getValue().stream()
                .map(activity -> new ActivitiesOnDate(
                        activity.getId().toString(),
                        activity.getTitle(),
                        activity.getOccursAt()
                ))
                .toList();
    }

    public LinkResponseDto createTripLink(UUID tripId, LinkRequestDto linkRequestDto) {
        var trip = getById(tripId);

        var link = linkService.createLink(linkRequestDto.toLink(trip));

        return new LinkResponseDto(link.getId().toString());
    }

    public List<LinksTripResponseDto> getAllLinks(UUID tripId) {
        var links = linkService.getAllLinksFromTrip(tripId);

        return links
                .stream()
                .map(link -> new LinksTripResponseDto(
                        link.getId(),
                        link.getTitle(),
                        link.getUrl()
                ))
                .toList();
    }
}
