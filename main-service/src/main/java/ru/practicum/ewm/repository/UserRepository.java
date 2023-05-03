package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByIdInOrderByIdAsc(Collection<Integer> ids, Pageable pageable);
}