package ru.practicum.ewm.controller.pub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.service.pub.CompilationPublicService;

import java.util.Collection;

public class CompilationPublicController {
    private final CompilationPublicService compilationPublicService;

    public CompilationPublicController(CompilationPublicService compilationPublicService) {
        this.compilationPublicService = compilationPublicService;
    }

    @GetMapping(path = "/compilations")
    public CategoryDto getCompilations(@RequestParam Boolean pinned,
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size) {
        return compilationPublicService.getCompilations(pinned, from, size);
    }

    @GetMapping(path = "/compilations/{compId}")
    public Collection<CategoryDto> getCompilation(@RequestParam Integer compId) {
        return compilationPublicService.getCompilation(compId);
    }
}
