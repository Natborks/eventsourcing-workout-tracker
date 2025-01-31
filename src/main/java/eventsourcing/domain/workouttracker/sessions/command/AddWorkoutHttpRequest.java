package eventsourcing.domain.workouttracker.sessions.command;

import eventsourcing.domain.workouttracker.sessions.aggregate.WorkoutType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddWorkoutHttpRequest {
    @NotNull WorkoutType workoutType;
    @NotNull String sessionId;
    @NotNull int reps;
    @NotNull float weight;
}
