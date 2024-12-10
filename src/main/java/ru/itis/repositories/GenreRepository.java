package ru.itis.repositories;

import ru.itis.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre> {

    Optional<Genre> findByName(String name);

    List<Genre> findByTitle(Long titleId);
}