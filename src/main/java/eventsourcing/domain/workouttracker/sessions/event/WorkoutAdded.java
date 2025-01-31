package eventsourcing.domain.workouttracker.sessions.event;

import eventsourcing.common.event.TransformationEvent;
import eventsourcing.domain.workouttracker.sessions.aggregate.Session;
import eventsourcing.domain.workouttracker.sessions.aggregate.Workout;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuperBuilder
@Getter
public class WorkoutAdded extends TransformationEvent<Session> {
    @NonNull Workout workout;
    @Override
    public Session transformAggregate(Session aggregate) {
        aggregate.getWorkouts().add(workout);

        return Session
                .builder()
                .aggregateId(aggregateId)
                .aggregateVersion(aggregateVersion)
                .workouts(aggregate.getWorkouts())
                .build();
    }
}
