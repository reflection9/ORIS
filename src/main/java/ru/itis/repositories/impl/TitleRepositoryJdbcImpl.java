package ru.itis.repositories.impl;

import ru.itis.models.Title;
import ru.itis.models.Genre;
import ru.itis.repositories.TitleGenreRepository;
import ru.itis.repositories.TitleRepository;
import ru.itis.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TitleRepositoryJdbcImpl implements TitleRepository {


    @Override
    public List<Title> findAll() {
        List<Title> titles = new ArrayList<>();
        String query = "SELECT * FROM titles";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String type = resultSet.getString("type");
                String coverImage = resultSet.getString("cover_image");
                Long authorId = resultSet.getLong("author_id");

                Title title = new Title(id, name, description, type, coverImage, authorId);
                title.setGenres(getGenresForTitle(id));
                title.setAuthorId(authorId);

                titles.add(title);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    @Override
    public Optional<Title> findById(Long id) {
        String query = "SELECT * FROM titles WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long titleId = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String type = resultSet.getString("type");
                    String coverImage = resultSet.getString("cover_image");
                    Long authorId = resultSet.getLong("author_id");

                    Title title = new Title(titleId, name, description, type, coverImage, authorId);
                    title.setGenres(getGenresForTitle(titleId));
                    return Optional.of(title);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Title entity) {
        String query = "INSERT INTO titles (name, description, type, cover_image, author_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setString(3, entity.getType());
            statement.setString(4, entity.getCoverImage());
            statement.setLong(5, entity.getAuthorId());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    }
                }
                if (entity.getGenres() != null) {
                    TitleGenreRepository titleGenreRepository = new TitleGenresRepositoryJdbcImpl();
                    for (Genre genre : entity.getGenres()) {
                        titleGenreRepository.addGenreToTitle(entity.getId(), genre.getId());
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Title entity) {
        String query = "UPDATE titles SET name = ?, description = ?, type = ?, cover_image = ?, author_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setString(3, entity.getType());
            statement.setString(4, entity.getCoverImage());
            statement.setLong(5, entity.getAuthorId());
            statement.setLong(6, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Title entity) {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) {
        String query = "DELETE FROM titles WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Title> findByName(String name) {
        String query = "SELECT * FROM titles WHERE name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long titleId = resultSet.getLong("id");
                    String titleName = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String type = resultSet.getString("type");
                    String coverImage = resultSet.getString("cover_image");
                    Long authorId = resultSet.getLong("author_id");

                    Title title = new Title(titleId, titleName, description, type, coverImage, authorId);
                    title.setGenres(getGenresForTitle(titleId)); // Загружаем жанры
                    return Optional.of(title);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Title> findByAuthor(Long authorId) {
        List<Title> titles = new ArrayList<>();
        String query = "SELECT * FROM titles WHERE author_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String type = resultSet.getString("type");
                    String coverImage = resultSet.getString("cover_image");

                    Title title = new Title(id, name, description, type, coverImage, authorId);
                    title.setGenres(getGenresForTitle(id));
                    titles.add(title);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    @Override
    public List<Title> findByGenre(Long genreId) {
        List<Title> titles = new ArrayList<>();
        String query = "SELECT t.* FROM titles t " +
                "JOIN title_genres tg ON t.id = tg.title_id " +
                "WHERE tg.genre_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, genreId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String type = resultSet.getString("type");
                    String coverImage = resultSet.getString("cover_image");
                    Long authorId = resultSet.getLong("author_id");

                    Title title = new Title(id, name, description, type, coverImage, authorId);
                    title.setGenres(getGenresForTitle(id));
                    titles.add(title);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    private List<Genre> getGenresForTitle(Long titleId) {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT g.* FROM genres g " +
                "JOIN title_genres tg ON g.id = tg.genre_id " +
                "WHERE tg.title_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, titleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    genres.add(new Genre(id, name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }
}
