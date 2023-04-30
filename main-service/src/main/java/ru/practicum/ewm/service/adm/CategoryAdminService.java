package ru.practicum.ewm.service.adm;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;

@Service
public interface CategoryAdminService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto editCategory(CategoryDto categoryDto);

    void deleteCategory(Integer catId);
}
