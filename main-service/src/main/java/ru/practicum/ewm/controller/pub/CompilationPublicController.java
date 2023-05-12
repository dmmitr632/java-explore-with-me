package ru.practicum.ewm.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class CompilationPublicController {
    private final CompilationService compilationService;

    public CompilationPublicController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping("/compilations")

    public List<CompilationDto> getCompilations(@RequestParam(defaultValue = "false") Boolean pinned,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение списка компиляций событий");
        log.info("========================================");
        log.info("                                                                           ");
        return new ArrayList<>(compilationService.getCompilations(pinned, from, size));
    }

    @GetMapping("/compilations/{compId}")

    public CompilationDto getCompilation(@PathVariable Integer compId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение события по compId {}", compId);
        log.info("========================================");
        log.info("                                                                           ");
        return compilationService.getCompilation(compId);
    }
}
