package eventsourcing.domain.workouttracker.sessions.aggregate;

public record Workout (
    WorkoutType workoutType,
    int reps,
    float weightInKg
){

}
