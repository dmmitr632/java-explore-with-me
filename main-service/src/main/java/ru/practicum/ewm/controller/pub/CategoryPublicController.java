package ru.practicum.ewm.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.service.CategoryService;

import java.util.Collection;

@RestController
@ResponseStatus(HttpStatus.OK)
@Slf4j
public class CategoryPublicController {

    private final CategoryService categoryService;

    public CategoryPublicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/categories/{catId}")
    public CategoryDto getCategory(@PathVariable Integer catId) {
        log.info("----------------------------------------------------------");
        log.info("Получение категории по catId {}", catId);
        log.info("----------------------------------------------------------");
        return categoryService.getCategory(catId);
    }

    @GetMapping(path = "/categories")
    public Collection<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        log.info("----------------------------------------------------------");
        log.info("Получение списка категорий");
        log.info("----------------------------------------------------------");
        return categoryService.getCategories(from, size);
    }
}