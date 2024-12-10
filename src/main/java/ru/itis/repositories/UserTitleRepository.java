package ru.itis.repositories;

import ru.itis.models.UserTitle;

import java.util.List;
import java.util.Optional;

public interface UserTitleRepository{


    void save(UserTitle userTitle);

    void update(UserTitle userTitle);

    Optional<UserTitle> findByUserAndTitle(Long userId, Long titleId);

    List<UserTitle> findByUser(Long userId);

    List<UserTitle> findByTitle(Long titleId);
}
