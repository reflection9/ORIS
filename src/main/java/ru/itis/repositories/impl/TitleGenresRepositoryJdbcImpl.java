package ru.itis.repositories.impl;

import ru.itis.repositories.TitleGenreRepository;
import ru.itis.utils.DatabaseConnection;

import java.sql.*;

public class TitleGenresRepositoryJdbcImpl implements TitleGenreRepository {

    @Override
    public void addGenreToTitle(Long titleId, Long genreId) {
        String query = "INSERT INTO title_genres (title_id, genre_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, titleId);
            statement.setLong(2, genreId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
    @Override
    public void removeGenreFromTitle(Long titleId, Long genreId) {
        String query = "DELETE FROM title_genres WHERE title_id = ? AND genre_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, titleId);
            statement.setLong(2, genreId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}