package tech.run.planner.service;

import org.springframework.stereotype.Service;
import tech.run.planner.controller.dto.participant.ParticipantRequestConfirmDto;
import tech.run.planner.entity.Participant;
import tech.run.planner.entity.Trip;
import tech.run.planner.exceptions.ParticipantNotFoundException;
import tech.run.planner.repository.ParticipantRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip) {

        List<Participant> participants = participantsToInvite
                .stream()
            .map(email -> new Participant("", email, false, trip))
                .toList();

        participantRepository.saveAll(participants);

        System.out.println(participants.getFirst().getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {

    }

    public void triggerConfirmationEmailToParticipant(String email) {
    }

    public void confirmParticipant(UUID participantId, ParticipantRequestConfirmDto dto) {
        var participant = getById(participantId);

        participant.setName(dto.name());
        participant.setConfirmed(true);

        participantRepository.save(participant);
    }

    public Participant getById(UUID participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(ParticipantNotFoundException::new);
    }

    public List<Participant> getAllParticipantsFromTrip(UUID tripId) {
        return participantRepository.findByTripId(tripId);
    }
}
