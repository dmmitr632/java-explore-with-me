package ru.practicum.ewm.controller.adm;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.service.adm.CompilationAdminService;

public class CompilationAdminController {
    private final CompilationAdminService compilationAdminService;

    public CompilationAdminController(CompilationAdminService compilationAdminService) {
        this.compilationAdminService = compilationAdminService;
    }

    @PostMapping(path = "/admin/compilations")
    public CompilationDto addCompilation(@PathVariable Integer compId) {
        return compilationAdminService.addCompilation(compId);
    }

    @DeleteMapping(path = "/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable Integer compId) {
        compilationAdminService.deleteCompilation(compId);
    }

    @PatchMapping(path = "/admin/compilations/{compId}")
    public CompilationDto editCompilation(@PathVariable Integer compId, @RequestBody CompilationDto compilationDto) {
        return compilationAdminService.editCompilation(compId, compilationDto);
    }
}
