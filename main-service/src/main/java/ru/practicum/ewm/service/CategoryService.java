package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;

import java.util.Collection;

public interface CategoryService {

    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto editCategory(CategoryDto categoryDto);

    void deleteCategory(Integer catId);

    CategoryDto getCategory(Integer catId);

    Collection<CategoryDto> getCategories(Integer from, Integer size);

}
