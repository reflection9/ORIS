package ru.itis.repositories;

import ru.itis.models.Chapter;

import java.util.List;

public interface ChapterRepository extends CrudRepository<Chapter> {

    List<Chapter> findByTitle(Long titleId);
}
