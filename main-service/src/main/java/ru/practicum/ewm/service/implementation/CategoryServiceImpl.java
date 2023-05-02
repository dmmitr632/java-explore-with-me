package ru.practicum.ewm.service.implementation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.service.CategoryService;

import java.util.Collection;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public CategoryDto editCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(Integer catId) {

    }

    @Override
    public CategoryDto getCategory(Integer catId) {
        return null;
    }

    @Override
    public Collection<CategoryDto> getCategories(Integer from, Integer size) {
        return null;
    }
}
