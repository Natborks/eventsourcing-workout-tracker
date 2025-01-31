package eventsourcing.domain.supertodos.todolist.command.starttodolist;

import eventsourcing.common.command.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class StartTodoListCommand extends Command {

    //Should match TodoListStarted Event (for properties that user has to supply)
    @NonNull private String name;


}
