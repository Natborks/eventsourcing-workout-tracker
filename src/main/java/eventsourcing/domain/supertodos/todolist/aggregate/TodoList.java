package eventsourcing.domain.supertodos.todolist.aggregate;

import eventsourcing.common.aggregate.Aggregate;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
public class TodoList extends Aggregate {
    @NonNull private String name;
    @NonNull private Integer itemCount;
}
