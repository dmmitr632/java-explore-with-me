package ru.practicum.ewm.controller.pub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationService;

import java.util.ArrayList;
import java.util.List;

public class CompilationPublicController {
    private final CompilationService compilationService;

    public CompilationPublicController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping(path = "/compilations")
    public List<CompilationDto> getCompilations(@RequestParam Boolean pinned,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return new ArrayList<>(compilationService.getCompilations(pinned, from, size));
    }

    @GetMapping(path = "/compilations/{compId}")
    public CompilationDto getCompilation(@PathVariable Integer compId) {
        return compilationService.getCompilation(compId);
    }
}
