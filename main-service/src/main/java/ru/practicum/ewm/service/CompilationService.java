package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationDto;

import javax.validation.Valid;
import java.util.Collection;

public interface CompilationService {

    CompilationDto addCompilation(@Valid NewCompilationDto compId);

    void deleteCompilation(Integer compId);

    CompilationDto editCompilation(Integer compId, UpdateCompilationDto updateCompilationDto);

    CompilationDto getCompilation(Integer compId);

    Collection<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);


}
