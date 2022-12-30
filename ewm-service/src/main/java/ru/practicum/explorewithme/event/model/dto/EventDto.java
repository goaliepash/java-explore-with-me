package ru.practicum.explorewithme.event.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.model.dto.CategoryDto;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.model.Location;
import ru.practicum.explorewithme.user.model.dto.UserShortDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private int confirmedRequests;

    private String createdOn;

    private String description;

    private String eventDate;

    private UserShortDto initiator;

    private Location location;

    private Boolean paid;

    private int participantLimit;

    private String publishedOn;

    private Boolean requestModeration;

    private EventState state;

    private String title;

    private int views;
}