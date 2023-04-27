package ru.practicum.ewm.controller.pub;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.service.pub.CategoryPublicService;

import java.util.Collection;

@RestController
@ResponseStatus(HttpStatus.OK)
public class CategoryPublicController {

    private final CategoryPublicService categoryPublicService;

    public CategoryPublicController(CategoryPublicService categoryPublicService) {
        this.categoryPublicService = categoryPublicService;
    }

    @GetMapping(path = "/categories/{catId}")
    public CategoryDto getCategory(@PathVariable Integer catId) {
        return categoryPublicService.getCategory(catId);
    }

    @GetMapping(path = "/categories")
    public Collection<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return categoryPublicService.getCategories(from, size);
    }
}