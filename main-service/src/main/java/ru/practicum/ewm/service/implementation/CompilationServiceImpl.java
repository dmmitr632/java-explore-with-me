package ru.practicum.ewm.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.CompilationService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }


    @Transactional
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
        compilation.setEvents(eventRepository.findAllById(newCompilationDto.getEvents()));

        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Integer compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new NotFoundException("Compilation not found");
        }
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto editCompilation(Integer compId, UpdateCompilationDto updateCompilationDto) {

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        if (updateCompilationDto.getPinned() != null) {
            compilation.setPinned(updateCompilationDto.getPinned());
        }
        if (updateCompilationDto.getTitle() != null) {
            compilation.setTitle(updateCompilationDto.getTitle());
        }
        compilation.setEvents(eventRepository.findAllById(updateCompilationDto.getEvents()));
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
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



