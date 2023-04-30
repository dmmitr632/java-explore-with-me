package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.User;

import java.awt.print.Pageable;
import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer> {
    Collection<User> findAllByIdInOrderByIdAsc(Collection<Integer> ids, Pageable pageable);
}