package eventsourcing.domain.workouttracker.sessions.query;

import eventsourcing.common.query.Query;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WorkoutsBySessionQuery extends Query {
}
