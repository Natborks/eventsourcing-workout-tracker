package eventsourcing.domain.workouttracker.sessions.command;

import eventsourcing.common.command.Command;
import eventsourcing.common.command.CommandHandler;
import eventsourcing.common.eventstore.PostgresTransactionalEventStore;
import eventsourcing.common.util.IdGenerator;
import eventsourcing.domain.workouttracker.sessions.event.SessionStarted;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;

@Service
@RequestScope
public class StartSessionCommandHandler extends CommandHandler {
    public StartSessionCommandHandler(PostgresTransactionalEventStore postgresTransactionalEventStore) {
        super(postgresTransactionalEventStore);
    }

    @Override
    public void handleCommand(Command command) {
        if (command instanceof StartSessionCommand startSessionCommand) {
            handleStartSessionCommand(startSessionCommand);
        } else {
            throw new IllegalArgumentException("Unsupported command type: " + command.getClass().getName());
        }
    }

    private void handleStartSessionCommand(StartSessionCommand startSessionCommand) {
        String eventId = IdGenerator.generateRandomId();
        String aggregateId = IdGenerator.generateRandomId();

        SessionStarted sessionStarted = SessionStarted
                .builder()
                .startTime(startSessionCommand.getStartTime())
                .aggregateId(aggregateId)
                .aggregateVersion(1)
                .correlationId(eventId)
                .causationId(eventId)
                .recordedOn(Instant.now())
                .eventId(eventId)
                .build();

        postgresTransactionalEventStore.saveEvent(sessionStarted);

    }
}
