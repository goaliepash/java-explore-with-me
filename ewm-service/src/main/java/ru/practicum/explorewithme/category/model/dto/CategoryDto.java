package ru.practicum.explorewithme.category.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.constraint_group.Create;
import ru.practicum.explorewithme.constraint_group.Update;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @NotNull(groups = {Create.class})
    private long id;

    @NotNull(groups = {Create.class, Update.class})
    private String name;
}