package ru.practicum.ewm.controller.adm;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationDto;
import ru.practicum.ewm.service.CompilationService;

import javax.validation.Valid;

public class CompilationAdminController {
    private final CompilationService compilationService;

    public CompilationAdminController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping(path = "/admin/compilations")
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping(path = "/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable Integer compId) {
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping(path = "/admin/compilations/{compId}")
    public CompilationDto editCompilation(@PathVariable Integer compId,
                                          @RequestBody UpdateCompilationDto updateCompilationDto) {
        return compilationService.editCompilation(compId, updateCompilationDto);
    }
}
