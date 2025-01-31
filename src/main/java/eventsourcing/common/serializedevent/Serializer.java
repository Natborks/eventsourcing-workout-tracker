package eventsourcing.common.serializedevent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eventsourcing.common.event.Event;
import eventsourcing.domain.cookingclub.membership.event.ApplicationEvaluated;
import eventsourcing.domain.cookingclub.membership.event.ApplicationSubmitted;
import eventsourcing.domain.supertodos.todolist.event.TodoListStarted;
import eventsourcing.domain.workouttracker.sessions.event.SessionStarted;
import eventsourcing.domain.workouttracker.sessions.event.WorkoutAdded;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class Serializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SerializedEvent serialize(Event event) {
        return SerializedEvent.builder()
                .eventId(event.getEventId())
                .aggregateId(event.getAggregateId())
                .aggregateVersion(event.getAggregateVersion())
                .correlationId(event.getCorrelationId())
                .causationId(event.getCausationId())
                .recordedOn(formatInstant(event.getRecordedOn()))
                .eventName(determineEventName(event))
                .jsonPayload(createJsonPayload(event))
                .jsonMetadata("{}")
                .build();
    }

    private String determineEventName(Event event) {
        if (event == null) {
            throw new RuntimeException("Event is null");
        }
        if (event instanceof ApplicationSubmitted) {
            return "CookingClub_Membership_ApplicationSubmitted";
        }
        if (event instanceof TodoListStarted) {
            return "SuperTodos_TodoList_TodoListStarted";
        }
        if (event instanceof ApplicationEvaluated) {
            return "CookingClub_Membership_ApplicationEvaluated";
        }
        if (event instanceof SessionStarted) {
            return "WorkoutTracker_Sessions_SessionStarted";
        }
        if (event instanceof WorkoutAdded){
            return "WorkoutTracker_Sessions_WorkoutAdded";
        }
        throw new RuntimeException("Unknown event type: " + event.getClass().getName());
    }

    private String createJsonPayload(Event event) {
        try {
            ObjectNode jsonPayload = objectMapper.createObjectNode();

            if (event instanceof ApplicationSubmitted applicationSubmitted) {
                jsonPayload.put("firstName", applicationSubmitted.getFirstName());
                jsonPayload.put("lastName", applicationSubmitted.getLastName());
                jsonPayload.put("favoriteCuisine", applicationSubmitted.getFavoriteCuisine());
                jsonPayload.put("yearsOfProfessionalExperience", applicationSubmitted.getYearsOfProfessionalExperience());
                jsonPayload.put("numberOfCookingBooksRead", applicationSubmitted.getNumberOfCookingBooksRead());
            } else if (event instanceof ApplicationEvaluated applicationEvaluated) {
                jsonPayload.put("evaluationOutcome", applicationEvaluated.getEvaluationOutcome().name());
            }else if (event instanceof TodoListStarted todoListStarted) {
                jsonPayload.put("name", todoListStarted.getName());
            } else if (event instanceof SessionStarted sessionStarted) {
                jsonPayload.put("startTime", sessionStarted.getStartTime().getDate()
                        + sessionStarted.getStartTime().getTime());
            } else if (event instanceof WorkoutAdded workoutAdded) {
                jsonPayload.put("workout", workoutAdded.getWorkout().workoutType().toString());
            }

            return objectMapper.writeValueAsString(jsonPayload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event payload", e);
        }
    }

    private String formatInstant(Instant instant) {
        ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS z").format(zdt);
    }
}