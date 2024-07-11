package tech.run.planner.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Participant() {
    }

    public Participant(String name, String email, boolean isConfirmed, Trip trip) {
        this.name = name;
        this.email = email;
        this.isConfirmed = isConfirmed;
        this.trip = trip;
    }

    public Participant(UUID id, String name, String email, boolean isConfirmed, Trip trip) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isConfirmed = isConfirmed;
        this.trip = trip;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
