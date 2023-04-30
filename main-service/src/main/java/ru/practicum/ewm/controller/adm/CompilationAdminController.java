package ru.practicum.ewm.controller.adm;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationDto;
import ru.practicum.ewm.service.adm.CompilationAdminService;

import javax.validation.Valid;

public class CompilationAdminController {
    private final CompilationAdminService compilationAdminService;

    public CompilationAdminController(CompilationAdminService compilationAdminService) {
        this.compilationAdminService = compilationAdminService;
    }

    @PostMapping(path = "/admin/compilations")
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return compilationAdminService.addCompilation(newCompilationDto);
    }

    @DeleteMapping(path = "/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable Integer compId) {
        compilationAdminService.deleteCompilation(compId);
    }

    @PatchMapping(path = "/admin/compilations/{compId}")
    public CompilationDto editCompilation(@PathVariable Integer compId,
                                          @RequestBody UpdateCompilationDto updateCompilationDto) {
        return compilationAdminService.editCompilation(compId, updateCompilationDto);
    }
}
