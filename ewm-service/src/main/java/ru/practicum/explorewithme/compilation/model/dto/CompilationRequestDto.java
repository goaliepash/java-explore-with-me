package ru.practicum.explorewithme.compilation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.constraint_group.Create;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationRequestDto {

    private Long id;

    @NotNull(groups = {Create.class})
    private Boolean pinned;

    @NotNull(groups = {Create.class})
    private String title;

    private Set<Long> events;
}