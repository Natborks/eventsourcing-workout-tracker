package eventsourcing.domain.workouttracker.sessions.query;

import eventsourcing.common.projection.MongoTransactionalProjectionOperator;
import eventsourcing.common.query.Query;
import eventsourcing.common.query.QueryHandler;
import eventsourcing.domain.workouttracker.sessions.projection.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class WorkoutsBySessionQueryHandler extends QueryHandler {
    SessionRepository sessionRepository;

    public WorkoutsBySessionQueryHandler(
            MongoTransactionalProjectionOperator mongoTransactionalProjectionOperator,
            SessionRepository sessionRepository) {
        super(mongoTransactionalProjectionOperator);
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Object handleQuery(Query query) {
        if (query instanceof WorkoutsBySessionQuery) {
            return sessionRepository.findAll();
        }

        throw new IllegalArgumentException("Unsupported query type: " + query.getClass().getName());
    }
}
