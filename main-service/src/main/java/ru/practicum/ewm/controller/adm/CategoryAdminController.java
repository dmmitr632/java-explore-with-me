package ru.practicum.ewm.controller.adm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.service.adm.CategoryAdminService;

import javax.validation.Valid;

@RestController
@ResponseStatus(HttpStatus.OK)
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    public CategoryAdminController(CategoryAdminService categoryAdminService) {
        this.categoryAdminService = categoryAdminService;
    }

    @PostMapping(path = "/admin/categories")
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return categoryAdminService.addCategory(newCategoryDto);
    }

    @PatchMapping(path = "/admin/categories")
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryAdminService.editCategory(categoryDto);
    }

    @DeleteMapping(path = "/admin/categories/{catId}")
    public void deleteCategory(@PathVariable Integer catId) {
        categoryAdminService.deleteCategory(catId);
    }
}