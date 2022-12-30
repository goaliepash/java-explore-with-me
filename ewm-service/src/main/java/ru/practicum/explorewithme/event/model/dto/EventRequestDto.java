package ru.practicum.explorewithme.event.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.constraint_group.Create;
import ru.practicum.explorewithme.constraint_group.Update;
import ru.practicum.explorewithme.event.model.Location;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto {

    @NotNull(groups = {Create.class})
    private String annotation;

    @NotNull(groups = {Create.class})
    private Long category;

    private String description;

    @NotNull(groups = {Create.class})
    private String eventDate;

    @NotNull(groups = {Update.class})
    private Long eventId;

    @NotNull(groups = {Create.class})
    private Location location;

    @NotNull(groups = {Create.class})
    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    @NotNull(groups = {Create.class})
    private String title;
}