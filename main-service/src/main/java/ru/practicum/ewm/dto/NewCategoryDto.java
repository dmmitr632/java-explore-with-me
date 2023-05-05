package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewCategoryDto {
    @NotEmpty
    String name;

}
