package eventsourcing.domain.supertodos.todolist.event;

import eventsourcing.common.event.TransformationEvent;
import eventsourcing.domain.supertodos.todolist.aggregate.TodoList;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
// a transformation event to edit the already created todolist
public class TodoItemAdded extends TransformationEvent<TodoList> {

    @NonNull private String itemName;
    @NonNull private String description;


    @Override
    public TodoList transformAggregate(TodoList aggregate) {
        return TodoList.builder()
                .aggregateId(aggregateId)
                .aggregateVersion(aggregateVersion)
                .name(aggregate.getName())
                .itemCount(aggregate.getItemCount() + 1)
                .build();
    }
}
