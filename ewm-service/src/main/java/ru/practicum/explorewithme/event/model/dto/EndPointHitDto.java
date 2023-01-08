package ru.practicum.explorewithme.event.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EndPointHitDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}