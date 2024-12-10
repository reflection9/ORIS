package ru.itis.repositories;

import ru.itis.models.Title;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends CrudRepository<Title> {

    Optional<Title> findByName(String name);

    List<Title> findByAuthor(Long authorId);

    List<Title> findByGenre(Long genreId);
}