package tech.run.planner.service;

import org.springframework.stereotype.Service;
import tech.run.planner.entity.Activity;
import tech.run.planner.repository.ActivityRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity createActivity(Activity activity) {
        return this.activityRepository.save(activity);
    }

    public List<Activity> getAllActivitiesFromTrip(UUID tripId) {
        return activityRepository.findByTripId(tripId);
    }
}
