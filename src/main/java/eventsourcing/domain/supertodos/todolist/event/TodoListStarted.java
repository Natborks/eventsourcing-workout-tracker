package eventsourcing.domain.supertodos.todolist.event;

import eventsourcing.common.event.CreationEvent;
import eventsourcing.domain.supertodos.todolist.aggregate.TodoList;
import lombok.Generated;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TodoListStarted extends CreationEvent<TodoList> {
    @NonNull private String name;

    @Override
    public TodoList createAggregate() {
        return TodoList.builder()
                .aggregateId(aggregateId)
                .aggregateVersion(aggregateVersion)
                .name(name)
                .itemCount(0)
                .build();
    }
}
