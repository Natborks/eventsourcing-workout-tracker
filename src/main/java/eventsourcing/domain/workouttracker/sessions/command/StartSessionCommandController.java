package eventsourcing.domain.workouttracker.sessions.command;

import eventsourcing.common.command.CommandController;
import eventsourcing.common.eventstore.PostgresTransactionalEventStore;
import eventsourcing.common.projection.MongoTransactionalProjectionOperator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Date;

@RestController
@RequestScope
@RequestMapping("/api/v1/workout-tracker/sessions/command")
public class StartSessionCommandController extends CommandController {
    private final StartSessionCommandHandler startSessionCommandHandler;

    public StartSessionCommandController(PostgresTransactionalEventStore postgresTransactionalEventStore,
                                         MongoTransactionalProjectionOperator mongoTransactionalProjectionOperator,
                                         StartSessionCommandHandler startSessionCommandHandler) {
        super(postgresTransactionalEventStore, mongoTransactionalProjectionOperator);
        this.startSessionCommandHandler = startSessionCommandHandler;
    }

    @PostMapping("/start-session")
    public void startSession() {
        StartSessionCommand startSessionCommand = StartSessionCommand.builder()
                .startTime(new Date())
                .build();

        processCommand(startSessionCommand, startSessionCommandHandler);
    }
}
