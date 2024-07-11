package tech.run.planner.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "occurs_at", nullable = false)
    private LocalDateTime occursAt;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Activity() {
    }

    public Activity(String title, LocalDateTime occursAt, Trip trip) {
        this.title = title;
        this.occursAt = occursAt;
        this.trip = trip;
    }

    public Activity(UUID id, String title, LocalDateTime occursAt, Trip trip) {
        this.id = id;
        this.title = title;
        this.occursAt = occursAt;
        this.trip = trip;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getOccursAt() {
        return occursAt;
    }

    public void setOccursAt(LocalDateTime occursAt) {
        this.occursAt = occursAt;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
