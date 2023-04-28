package ru.practicum.ewm.service.adm;

import ru.practicum.ewm.dto.CompilationDto;

public interface CompilationAdminService {
    CompilationDto addCompilation(Integer compId);

    void deleteCompilation(Integer compId);

    CompilationDto editCompilation(Integer compId, CompilationDto compilationDto);
}
