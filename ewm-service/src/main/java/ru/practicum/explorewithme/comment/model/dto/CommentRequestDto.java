package ru.practicum.explorewithme.comment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class CommentRequestDto {
    @JsonProperty("text")
    @NotBlank
    private String text;
}