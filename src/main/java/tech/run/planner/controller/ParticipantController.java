package tech.run.planner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.run.planner.controller.dto.participant.ParticipantRequestConfirmDto;
import tech.run.planner.service.ParticipantService;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PatchMapping("/{participantId}/confirm")
    public ResponseEntity<Void> confirmParticipant(@PathVariable UUID participantId, @RequestBody ParticipantRequestConfirmDto confirmDto) {
        participantService.confirmParticipant(participantId, confirmDto);

        return ResponseEntity.noContent().build();
    }
}
