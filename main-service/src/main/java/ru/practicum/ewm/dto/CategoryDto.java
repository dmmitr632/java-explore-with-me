package ru.practicum.ewm.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDto {
    Integer id;

    @NotEmpty
    String name;
}
