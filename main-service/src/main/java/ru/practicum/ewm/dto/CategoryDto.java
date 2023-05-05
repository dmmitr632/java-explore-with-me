package ru.practicum.ewm.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    Integer id;

    @NotEmpty
    String name;
}
