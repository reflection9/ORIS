package ru.itis.repositories;

import ru.itis.models.Author;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author> {

    Optional<Author> findByName(String name);
}
