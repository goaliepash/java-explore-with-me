package ru.practicum.explorewithme.event.model.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.model.dto.CategoryMapper;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.user.model.dto.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Event fromEventRequestDto(EventRequestDto eventRequestDto, Category category) {
        Event event = new Event();
        event.setAnnotation(eventRequestDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(eventRequestDto.getDescription());
        event.setEventDate(LocalDateTime.parse(eventRequestDto.getEventDate(), FORMATTER));
        event.setLocation(eventRequestDto.getLocation());
        event.setPaid(eventRequestDto.getPaid());
        event.setParticipantLimit(eventRequestDto.getParticipantLimit());
        event.setRequestModeration(eventRequestDto.getRequestModeration());
        event.setTitle(eventRequestDto.getTitle());
        return event;
    }

    public static Event fromEventDto(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setAnnotation(eventDto.getAnnotation());
        event.setCategory(CategoryMapper.fromCategoryDto(eventDto.getCategory()));
        event.setConfirmedRequests(eventDto.getConfirmedRequests());
        event.setCreatedOn(LocalDateTime.parse(eventDto.getCreatedOn(), FORMATTER));
        event.setDescription(eventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(), FORMATTER));
        event.setInitiator(UserMapper.fromUserShortDto(eventDto.getInitiator()));
        event.setLocation(eventDto.getLocation());
        event.setPaid(eventDto.getPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setPublishedOn(LocalDateTime.parse(eventDto.getPublishedOn(), FORMATTER));
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setState(eventDto.getState());
        event.setTitle(eventDto.getTitle());
        event.setViews(eventDto.getViews());
        return event;
    }

    public static EventRequestDto toEventRequestDto(Event event) {
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setAnnotation(event.getAnnotation());
        eventRequestDto.setCategory(event.getCategory().getId());
        eventRequestDto.setDescription(event.getDescription());
        eventRequestDto.setEventDate(event.getEventDate().toString());
        eventRequestDto.setLocation(event.getLocation());
        eventRequestDto.setPaid(event.getPaid());
        eventRequestDto.setParticipantLimit(event.getParticipantLimit());
        eventRequestDto.setRequestModeration(event.getRequestModeration());
        eventRequestDto.setTitle(event.getTitle());
        return eventRequestDto;
    }

    public static EventDto toEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setAnnotation(event.getAnnotation());
        eventDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventDto.setConfirmedRequests(event.getConfirmedRequests());
        if (event.getCreatedOn() != null) {
            eventDto.setCreatedOn(event.getCreatedOn().format(FORMATTER));
        }
        eventDto.setDescription(event.getDescription());
        eventDto.setEventDate(event.getEventDate().format(FORMATTER));
        eventDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventDto.setLocation(event.getLocation());
        eventDto.setPaid(event.getPaid());
        eventDto.setParticipantLimit(event.getParticipantLimit());
        if (event.getPublishedOn() != null) {
            eventDto.setPublishedOn(event.getPublishedOn().format(FORMATTER));
        }
        eventDto.setRequestModeration(event.getRequestModeration());
        eventDto.setState(event.getState());
        eventDto.setTitle(event.getTitle());
        eventDto.setViews(event.getViews());
        return eventDto;
    }
}