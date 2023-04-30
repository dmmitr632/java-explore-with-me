package ru.practicum.ewm.service.pub;

import ru.practicum.ewm.dto.CompilationDto;

import java.util.Collection;

public interface CompilationPublicService {
    CompilationDto getCompilation(Integer compId);

    Collection<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);
}