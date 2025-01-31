package eventsourcing.domain.workouttracker.sessions.command;

import eventsourcing.common.aggregate.Aggregate;
import eventsourcing.common.command.Command;
import eventsourcing.common.command.CommandHandler;
import eventsourcing.common.eventstore.AggregateAndEventIdsInLastEvent;
import eventsourcing.common.eventstore.PostgresTransactionalEventStore;
import eventsourcing.common.util.IdGenerator;
import eventsourcing.domain.workouttracker.sessions.event.SessionStarted;
import eventsourcing.domain.workouttracker.sessions.event.WorkoutAdded;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;

@Service
@RequestScope
public class AddWorkoutCommandHandler extends CommandHandler {

    public AddWorkoutCommandHandler(PostgresTransactionalEventStore
                                            postgresTransactionalEventStore) {
        super(postgresTransactionalEventStore);
    }

    @Override
    public void handleCommand(Command command) {
        if (command instanceof AddWorkoutCommand addWorkoutCommand) {
            handleAddWorkoutCommand(addWorkoutCommand);
        }
    }

    private void handleAddWorkoutCommand(AddWorkoutCommand addWorkoutCommand) {
        String eventId = IdGenerator.generateRandomId();

        AggregateAndEventIdsInLastEvent aggregate = postgresTransactionalEventStore
                .findAggregate(addWorkoutCommand.getSessionId());

        WorkoutAdded workoutAdded = WorkoutAdded
                .builder()
                .aggregateId(aggregate.getAggregate().getAggregateId())
                .aggregateVersion(aggregate.getAggregate().getAggregateVersion() + 1)
                .correlationId(aggregate.getCorrelationIdOfLastEvent())
                .causationId(aggregate.getEventIdOfLastEvent())
                .recordedOn(Instant.now())
                .eventId(eventId)
                .workout(addWorkoutCommand.workout)
                .build();

        postgresTransactionalEventStore.saveEvent(workoutAdded);
    }
}
