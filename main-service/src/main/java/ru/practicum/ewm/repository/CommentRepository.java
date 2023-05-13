package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByUserId(Integer userId);

    List<Comment> findAllByEventId(Integer eventId, Pageable pageable);


    @Query(value = "SELECT user_id " +
            "FROM comments " +
            "WHERE id = ?1", nativeQuery = true)
    Integer getUserIdOfComment(Integer commentId);


}
