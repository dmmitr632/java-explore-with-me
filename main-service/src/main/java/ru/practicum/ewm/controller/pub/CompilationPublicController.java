package ru.practicum.ewm.controller.pub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.service.CompilationService;

import java.util.Collection;

public class CompilationPublicController {
    private final CompilationService compilationService;

    public CompilationPublicController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping(path = "/compilations")
    public CategoryDto getCompilations(@RequestParam Boolean pinned,
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping(path = "/compilations/{compId}")
    public Collection<CategoryDto> getCompilation(@PathVariable Integer compId) {
        return compilationService.getCompilation(compId);
    }
}
