package eventsourcing.domain.workouttracker.sessions.projection;

import eventsourcing.domain.workouttracker.sessions.aggregate.Workout;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Session {
    @Id @NonNull private String id;
    @NonNull private List<Workout> workouts;
}
