package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private Integer id;
    @NotEmpty
    private String name;
}
