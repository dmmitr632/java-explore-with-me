package ru.practicum.ewm.service.implementation;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.service.CategoryService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Admin services

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(newCategoryDto)));
    }

    @Override
    public CategoryDto editCategory(CategoryDto categoryDto) {
        categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new NotFoundException("Такой категории " +
                "пока не существует, редактирование невозможно"));
        Category category = Category.builder().id(categoryDto.getId()).name(categoryDto.getName()).build();
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Integer catId) {
        categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("Такой категории " +
                "пока не существует, удаление невозможно"));
    }

    // Public services

    @Override
    public CategoryDto getCategory(Integer catId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId).orElseThrow(()
                -> new NotFoundException("Такой категории пока не существует")));
    }

    @Override
    public Collection<CategoryDto> getCategories(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size))
                .stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }
}
