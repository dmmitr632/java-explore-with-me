package ru.practicum.ewm.controller.adm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.service.CategoryService;

import javax.validation.Valid;

@RestController
@ResponseStatus(HttpStatus.OK)
@Slf4j
public class CategoryAdminController {

    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/admin/categories")
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("----------------------------------------------------------");
        log.info("Добавление администратором категории {}", newCategoryDto);
        log.info("----------------------------------------------------------");
        return categoryService.addCategory(newCategoryDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/admin/categories/{catId}")
    public CategoryDto editCategory(@PathVariable Integer catId, @RequestBody @Valid CategoryDto categoryDto) {
        log.info("----------------------------------------------------------");
        log.info("Изменение администратором категории {}", categoryDto);
        log.info("----------------------------------------------------------");
        return categoryService.editCategory(catId, categoryDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/admin/categories/{catId}")
    public void deleteCategory(@PathVariable Integer catId) {
        log.info("----------------------------------------------------------");
        log.info("Удаление администратором категории в id {}", catId);
        log.info("----------------------------------------------------------");
        categoryService.deleteCategory(catId);
    }
}