package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.model.Compilation;

import java.util.stream.Collectors;

public class CompilationMapper {
    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return new Compilation(newCompilationDto.getTitle(), newCompilationDto.getPinned());
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
//        return new CompilationDto(compilation.getId(), compilation.getTitle(),
//                compilation.getPinned(), compilation.getEvents().stream()
//                .map(EventMapper::toEventShortDto).collect(Collectors.toList()));

        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream().map(EventMapper::toEventShortDto).collect(Collectors.toList()))
                .build();
    }
}
