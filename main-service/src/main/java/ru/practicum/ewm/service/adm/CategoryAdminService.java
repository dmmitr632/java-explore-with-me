package ru.practicum.ewm.service.adm;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;

@Service
public interface CategoryAdminService {
    public CategoryDto addCategory(NewCategoryDto newCategoryDto);

    public CategoryDto editCategory(CategoryDto categoryDto);

    public void deleteCategory(Integer catId);
}
