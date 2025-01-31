package eventsourcing.domain.supertodos.todolist.command.starttodolist;

import eventsourcing.common.command.Command;
import eventsourcing.common.command.CommandHandler;
import eventsourcing.common.eventstore.PostgresTransactionalEventStore;
import eventsourcing.common.util.IdGenerator;
import eventsourcing.domain.supertodos.todolist.event.TodoListStarted;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Instant;

@Service
@RequestScope
public class StartTodoListCommandHandler extends CommandHandler {

    public StartTodoListCommandHandler(PostgresTransactionalEventStore
                                               postgresTransactionalEventStore) {
        super(postgresTransactionalEventStore);
    }

    @Override
    public void handleCommand(Command command) {
        if (command instanceof StartTodoListCommand startTodoListCommand) {
            handleStartTodoList(startTodoListCommand);
        } else {
            throw new IllegalArgumentException("Unsupported command type: " + command.getClass().getName());
        }
    }

    private void handleStartTodoList(StartTodoListCommand command) {
        String eventId = IdGenerator.generateRandomId();
        String aggregateId = IdGenerator.generateRandomId();

        TodoListStarted todoListStarted = TodoListStarted.builder()
                .name(command.getName())
                .eventId(eventId)
                .aggregateId(aggregateId)
                .aggregateVersion(1)
                .correlationId(eventId)
                .causationId(eventId)
                .recordedOn(Instant.now())
                .build();

        postgresTransactionalEventStore.saveEvent(todoListStarted);
    }
}
