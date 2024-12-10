package ru.itis.repositories;

import java.util.List;

public interface TitleGenreRepository {


    void addGenreToTitle(Long titleId, Long genreId);


    void removeGenreFromTitle(Long titleId, Long genreId);
}
