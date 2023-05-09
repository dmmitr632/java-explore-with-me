package ru.practicum.ewm.controller.adm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;
import ru.practicum.ewm.service.CompilationService;

import javax.validation.Valid;

@RestController
@Slf4j
public class CompilationAdminController {
    private final CompilationService compilationService;

    public CompilationAdminController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Добавление администратором компиляции событий {}", newCompilationDto);
        log.info("========================================");
        log.info("                                                                           ");
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer compId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Удаление администратором компиляции событий с id {}", compId);
        log.info("========================================");
        log.info("                                                                           ");
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto editCompilation(@PathVariable Integer compId,
                                          @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Изменение администратором компиляции событий с id {}, updateCompilationRequest {}", compId,
                updateCompilationRequest);
        log.info("========================================");
        log.info("                                                                           ");
        return compilationService.editCompilation(compId, updateCompilationRequest);
    }
}
