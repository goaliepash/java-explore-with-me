package ru.practicum.explorewithme.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.constraint_group.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(groups = {Create.class})
    private long id;

    @NotNull(groups = {Create.class})
    private String name;

    @NotNull(groups = {Create.class})
    @Email(groups = {Create.class})
    private String email;
}