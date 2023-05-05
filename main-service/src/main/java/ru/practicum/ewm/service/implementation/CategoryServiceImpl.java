package ru.practicum.ewm.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.exception.FieldValidationException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.service.CategoryService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Admin services

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        if (newCategoryDto.getName() == null) {
            throw new FieldValidationException("Отстутствует необходимое поле name, у newCategoryDto " +
                    newCategoryDto);
        }
        Category category = CategoryMapper.toCategory(newCategoryDto);
        categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto editCategory(Integer catId, CategoryDto categoryDto) {
        categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("Такой категории " +
                "пока не существует, редактирование невозможно"));
        Category category = Category.builder().id(catId).name(categoryDto.getName()).build();
        categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
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
