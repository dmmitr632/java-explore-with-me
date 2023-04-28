package ru.practicum.ewm.service.pub.implementation;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.service.pub.CompilationPublicService;

import java.util.Collection;

public class CompilationPublicServiceImpl implements CompilationPublicService {
    @Override
    public Collection<CategoryDto> getCompilation(Integer compId) {
        return null;
    }

    @Override
    public CategoryDto getCompilations(Boolean pinned, Integer from, Integer size) {
        return null;
    }
}
