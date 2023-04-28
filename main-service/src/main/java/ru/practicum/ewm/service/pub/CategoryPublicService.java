package ru.practicum.ewm.service.pub;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;

import java.util.Collection;

@Service
public interface CategoryPublicService {
    public CategoryDto getCategory(Integer catId);

    public Collection<CategoryDto> getCategories(Integer from, Integer size);
}
