package eventsourcing.domain.workouttracker.sessions.command;

import eventsourcing.common.command.CommandController;
import eventsourcing.common.eventstore.PostgresTransactionalEventStore;
import eventsourcing.common.projection.MongoTransactionalProjectionOperator;
import eventsourcing.domain.workouttracker.sessions.aggregate.Workout;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Date;

@RestController
@RequestScope
@RequestMapping("/api/v1/workout-tracker/sessions/command")
public class AddWorkoutCommandController extends CommandController {
    private final AddWorkoutCommandHandler addWorkoutCommandHandler;

    public AddWorkoutCommandController(PostgresTransactionalEventStore postgresTransactionalEventStore,
                                       MongoTransactionalProjectionOperator mongoTransactionalProjectionOperator,
                                       AddWorkoutCommandHandler addWorkoutCommandHandler) {
        super(postgresTransactionalEventStore, mongoTransactionalProjectionOperator);
        this.addWorkoutCommandHandler = addWorkoutCommandHandler;
    }

    @PostMapping("/add-workout")
    public void addWorkout(@Valid @RequestBody AddWorkoutHttpRequest addWorkoutHttpRequest) {
        Workout workout = new Workout(
                addWorkoutHttpRequest.getWorkoutType(),
                addWorkoutHttpRequest.getReps(),
                addWorkoutHttpRequest.getWeight());


        AddWorkoutCommand addWorkoutCommand = AddWorkoutCommand.builder()
                .workout(workout)
                .sessionId(addWorkoutHttpRequest.getSessionId())
                .build();

        processCommand(addWorkoutCommand, addWorkoutCommandHandler);
    }
}
