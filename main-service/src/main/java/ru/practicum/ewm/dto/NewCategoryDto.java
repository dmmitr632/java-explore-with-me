package ru.practicum.ewm.dto;

import javax.validation.constraints.NotEmpty;

public class NewCategoryDto {
    @NotEmpty
    String name;

    public NewCategoryDto(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
