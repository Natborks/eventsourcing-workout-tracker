package eventsourcing.domain.workouttracker.sessions.command;

import eventsourcing.common.command.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;

@Builder
@Getter
public class StartSessionCommand extends Command {

    @NonNull private Date startTime;

}
