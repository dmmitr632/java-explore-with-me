package ru.practicum.ewm.service.adm.implementation;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.service.adm.CompilationAdminService;

import javax.validation.Valid;

public class CompilationAdminServiceImpl implements CompilationAdminService {
    @Override
    public CompilationDto addCompilation(@Valid NewCompilationDto compId) {
        return null;
    }

    @Override
    public void deleteCompilation(Integer compId) {

    }

    @Override
    public CompilationDto editCompilation(Integer compId, CompilationDto compilationDto) {
        return null;
    }
}
