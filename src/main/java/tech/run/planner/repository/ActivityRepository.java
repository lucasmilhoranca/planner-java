package tech.run.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.run.planner.entity.Activity;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findByTripId(UUID tripId);
}
