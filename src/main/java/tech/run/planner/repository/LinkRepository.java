package tech.run.planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.run.planner.entity.Link;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {
    List<Link> findByTripId(UUID tripId);
}
