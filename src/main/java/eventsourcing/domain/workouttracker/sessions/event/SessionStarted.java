package eventsourcing.domain.workouttracker.sessions.event;


import eventsourcing.common.event.CreationEvent;
import eventsourcing.domain.workouttracker.sessions.aggregate.Session;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;

@SuperBuilder
@Getter
public class SessionStarted extends CreationEvent<Session> {

    private Date startTime;
    @Override
    public Session createAggregate() {
        return Session
                .builder()
                .startTime(new Date())
                .aggregateId(aggregateId)
                .aggregateVersion(aggregateVersion)
                .workouts(new ArrayList<>())
                .build();
    }
}
