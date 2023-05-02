package ru.practicum.ewm.mapper;


import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.model.Category;

public class CategoryMapper {

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return new Category(newCategoryDto.getName());
    }

    public static Category toCategory(CategoryDto CategoryDto) {
        return new Category(CategoryDto.getName());
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static NewCategoryDto toNewCategoryDto(CategoryDto categoryDto) {
        return new NewCategoryDto(categoryDto.getName());
    }

}
