package tech.run.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.run.planner.entity.Participant;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    List<Participant> findByTripId(UUID tripId);
}
