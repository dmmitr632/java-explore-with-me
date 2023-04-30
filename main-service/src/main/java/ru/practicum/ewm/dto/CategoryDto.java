package ru.practicum.ewm.dto;

import javax.validation.constraints.NotEmpty;

public class CategoryDto {
    Integer id;
    @NotEmpty
    String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


}
