package eventsourcing.domain.supertodos.todolist.command.starttodolist;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StartTodoListHttpRequest {
    @NotNull private String name;
}
