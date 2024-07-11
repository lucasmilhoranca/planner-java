package tech.run.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.run.planner.entity.Trip;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
