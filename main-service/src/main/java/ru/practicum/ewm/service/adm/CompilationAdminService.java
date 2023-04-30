package ru.practicum.ewm.service.adm;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;

import javax.validation.Valid;

public interface CompilationAdminService {
    CompilationDto addCompilation(@Valid NewCompilationDto compId);

    void deleteCompilation(Integer compId);

    CompilationDto editCompilation(Integer compId, CompilationDto compilationDto);
}
