package eventsourcing.domain.workouttracker.sessions.projection;

import eventsourcing.common.projection.MongoTransactionalProjectionOperator;
import eventsourcing.domain.cookingclub.membership.projection.membersbycuisine.Cuisine;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

@RequestScope
@Service
@RequiredArgsConstructor
public class SessionRepository {
    private final MongoTransactionalProjectionOperator mongoTransactionalProjectionOperator;
    private static final String COLLECTION_NAME = "WorkoutTracker_Sessions_Session";

    public void save(final Session session) {
        mongoTransactionalProjectionOperator.operate().save(session, COLLECTION_NAME);
    }

    public Optional<Cuisine> findOneById(final String id) {
        return Optional.ofNullable(mongoTransactionalProjectionOperator.operate().findOne(
                Query.query(Criteria.where("id").is(id)),
                Cuisine.class,
                COLLECTION_NAME
        ));
    }

    public List<Session> findAll() {
        return mongoTransactionalProjectionOperator.operate().find(
                new Query(),
                Session.class,
                COLLECTION_NAME
        );
    }
}
