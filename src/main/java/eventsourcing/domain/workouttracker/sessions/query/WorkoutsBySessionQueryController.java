package eventsourcing.domain.workouttracker.sessions.query;

import eventsourcing.common.projection.MongoTransactionalProjectionOperator;
import eventsourcing.common.query.QueryController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;


@RestController
@RequestScope
@RequestMapping("/api/workout/session/query")
public class WorkoutsBySessionQueryController extends QueryController {
    private final WorkoutsBySessionQueryHandler workoutsBySessionQueryHandler;

    public WorkoutsBySessionQueryController(
            MongoTransactionalProjectionOperator mongoTransactionalProjectionOperator,
            WorkoutsBySessionQueryHandler workoutsBySessionQueryHandler
    ) {
        super(mongoTransactionalProjectionOperator);
        this.workoutsBySessionQueryHandler = workoutsBySessionQueryHandler;
    }

    @PostMapping("/workouts-by-session")
    @ResponseStatus(HttpStatus.OK)
    public Object listWorkoutsBySession() {
        WorkoutsBySessionQuery query = WorkoutsBySessionQuery.builder().build();
        return processQuery(query, workoutsBySessionQueryHandler);
    }
}
