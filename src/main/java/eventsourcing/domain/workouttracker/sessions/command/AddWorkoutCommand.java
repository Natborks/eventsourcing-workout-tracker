package eventsourcing.domain.workouttracker.sessions.command;

import eventsourcing.common.command.Command;
import eventsourcing.domain.workouttracker.sessions.aggregate.Workout;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class AddWorkoutCommand extends Command {

    @NonNull Workout workout;

    @NonNull String sessionId;

}
