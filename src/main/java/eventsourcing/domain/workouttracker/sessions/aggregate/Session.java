package eventsourcing.domain.workouttracker.sessions.aggregate;

import eventsourcing.common.aggregate.Aggregate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
public class Session extends Aggregate {
    List<Workout> workouts;
    Date startTime;
    Date endTime;
}
