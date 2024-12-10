package ru.itis.repositories;

import ru.itis.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment>{
    List<Comment> findAll();

    Optional<Comment> findById(Long id);

    List<Comment> findByChapterId(Long chapterId);

    List<Comment> findByUserId(Long userId);
}