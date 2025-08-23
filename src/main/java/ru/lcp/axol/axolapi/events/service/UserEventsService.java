package ru.lcp.axol.axolapi.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lcp.axol.axolapi.auth.entity.CustomUserDetails;
import ru.lcp.axol.axolapi.auth.repository.UserRepository;
import ru.lcp.axol.axolapi.events.dto.UserEventRequest;
import ru.lcp.axol.axolapi.events.dto.UserEventResponse;
import ru.lcp.axol.axolapi.events.entity.UserEvent;
import ru.lcp.axol.axolapi.events.exceptions.notfound.UserEventNotFoundException;
import ru.lcp.axol.axolapi.events.repository.UserEventsRepository;
import ru.lcp.axol.axolapi.friend.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEventsService {
    private final UserEventsRepository userEventsRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> getUserEvents(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(getUserEventsByUserId(userDetails.getId()));
    }

    public ResponseEntity<?> getUserEvents(String userTag) {
        // TODO: Integration with user/auth service
        UUID userId = userRepository.findByUserTag(userTag)
                .orElseThrow(UserNotFoundException::new)
                .getId();

        // Continue user events service logic

        return ResponseEntity.ok(getUserEventsByUserId(userId));
    }


    @Transactional
    public ResponseEntity<?> addUserEvent(Authentication authentication, UserEventRequest userEventRequest) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        UserEvent userEvent = new UserEvent();
        userEvent.setEventName(userEventRequest.getEventName());
        userEvent.setUserId(userDetails.getId());
        userEvent.setEventStart(userEventRequest.getEventStartDate());
        userEvent.setEventEnd(userEventRequest.getEventEndDate());

        userEventsRepository.save(userEvent);

        return ResponseEntity.ok("Событие успешно создано");
    }

    @Transactional
    public ResponseEntity<?> deleteUserEvent(Authentication authentication, UUID eventId) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Optional<UserEvent> optionalUserEvent = userEventsRepository.getUserEventByIdAndUserId(eventId,userDetails.getId());

        if (optionalUserEvent.isEmpty()) {
            throw new UserEventNotFoundException();
        }

        userEventsRepository.delete(optionalUserEvent.get());

        return ResponseEntity.ok("Собыите удалено");
    }

    private List<UserEventResponse> getUserEventsByUserId(UUID userId) {
        return userEventsRepository.getUserEventByUserId(userId).stream().map((event) -> {
            UserEventResponse userEventResponse = new UserEventResponse();
            userEventResponse.setId(event.getId());
            userEventResponse.setEventName(event.getEventName());
            userEventResponse.setEventStartDate(event.getEventStart());
            userEventResponse.setEventEndDate(event.getEventEnd());

            return userEventResponse;
        }).toList();
    }
}
