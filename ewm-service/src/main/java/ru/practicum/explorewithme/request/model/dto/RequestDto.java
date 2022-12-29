package ru.practicum.explorewithme.request.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.request.model.RequestStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private Long id;

    private LocalDateTime created;

    private Long event;

    private Long requester;

    private RequestStatus status;
}