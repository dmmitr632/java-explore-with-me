package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;

import java.util.Collection;

public interface CompilationService {

    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Integer compId);

    CompilationDto editCompilation(Integer compId, UpdateCompilationRequest updateCompilationRequest);

    CompilationDto getCompilation(Integer compId);

    Collection<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);


}
