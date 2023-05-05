package ru.practicum.ewm.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;
import ru.practicum.ewm.exception.FieldValidationException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.repository.CompilationRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.CompilationService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;


    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository,
                                  ParticipationRequestRepository participationRequestRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        if (newCompilationDto.getTitle() == null) {
            throw new FieldValidationException("Некоррректный newCompilationDto, отсутствует обязательное поле title " +
                    newCompilationDto);
        }
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
        compilation.setEvents(eventRepository.findAllById(newCompilationDto.getEvents()));
        log.info("---------------------------------------------------------------------------");
        log.info("CompilationServiceImpl addCompilation, compilation после setEvents {}", compilation);
        log.info("---------------------------------------------------------------------------");
        compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(compilation);
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
    public CompilationDto editCompilation(Integer compId, UpdateCompilationRequest updateCompilationRequest) {

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Компиляции не существут"));
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        compilation.setEvents(eventRepository.findAllById(updateCompilationRequest.getEvents()));
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public CompilationDto getCompilation(Integer compId) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(compId).orElseThrow(()
                -> new NotFoundException("Компиляции не существут")));
    }

    @Override
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


    // Additional methods, work in progress

    public void deleteCompilationEvent(Integer compId, Integer eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(()
                -> new NotFoundException("Компиляции не существут"));
        Event event = eventRepository.findById(eventId).orElseThrow(()
                -> new NotFoundException("События не существут"));
        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);
    }

}




