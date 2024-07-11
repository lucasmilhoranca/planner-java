package tech.run.planner.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.run.planner.exceptions.ParticipantNotFoundException;
import tech.run.planner.exceptions.TripNotFoundException;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<Void> handlerTripNotFound(TripNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<Void> handlerParticipantNotFound(ParticipantNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
