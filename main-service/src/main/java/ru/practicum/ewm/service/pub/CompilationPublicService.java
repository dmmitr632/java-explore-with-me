package ru.practicum.ewm.service.pub;

import ru.practicum.ewm.dto.CategoryDto;

import java.util.Collection;

public interface CompilationPublicService {
    Collection<CategoryDto> getCompilation(Integer compId);

    CategoryDto getCompilations(Boolean pinned, Integer from, Integer size);
}
