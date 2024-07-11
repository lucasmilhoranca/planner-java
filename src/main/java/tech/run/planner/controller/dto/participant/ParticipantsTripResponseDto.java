package tech.run.planner.controller.dto.participant;

import java.util.UUID;

public record ParticipantsTripResponseDto(UUID participantId, String name, String email, Boolean isConfirmed) {
}
