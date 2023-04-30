package ru.practicum.ewm.service.pub.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.service.pub.CompilationPublicService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CompilationPublicServiceImpl implements CompilationPublicService {
    private final CompilationRepository compilationRepository;

    public CompilationPublicServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    public CompilationDto getCompilation(Integer compId) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(compId).orElseThrow(()
                -> new NotFoundException("Compilation not found")));
    }

    public Collection<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Page<Compilation> compilations;
        Pageable pageable = PageRequest.of(from, size);
        if (pinned != null) {
            compilations = compilationRepository.findAllByPinnedOrderByIdAsc(pinned, pageable);
        } else {
            compilations = compilationRepository.findAll(pageable);
        }
        return compilations.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }
}
