package ru.itis.repositories.impl;

import ru.itis.models.UserTitle;
import ru.itis.repositories.UserTitleRepository;
import ru.itis.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserTitleRepositoryJdbcImpl implements UserTitleRepository {

    @Override
    public void save(UserTitle userTitle) {
        String query = "INSERT INTO user_titles (user_id, title_id, status) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userTitle.getUserId());
            statement.setLong(2, userTitle.getTitleId());
            statement.setString(3, userTitle.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserTitle userTitle) {
        String query = "UPDATE user_titles SET status = ? WHERE user_id = ? AND title_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userTitle.getStatus());
            statement.setLong(2, userTitle.getUserId());
            statement.setLong(3, userTitle.getTitleId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<UserTitle> findByUserAndTitle(Long userId, Long titleId) {
        String query = "SELECT * FROM user_titles WHERE user_id = ? AND title_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, titleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String status = resultSet.getString("status");
                    UserTitle userTitle = new UserTitle(userId, titleId, status);
                    return Optional.of(userTitle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserTitle> findByUser(Long userId) {
        String query = "SELECT * FROM user_titles WHERE user_id = ?";
        List<UserTitle> userTitles = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long titleId = resultSet.getLong("title_id");
                    String status = resultSet.getString("status");
                    UserTitle userTitle = new UserTitle(userId, titleId, status);
                    userTitles.add(userTitle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userTitles;
    }

    @Override
    public List<UserTitle> findByTitle(Long titleId) {
        String query = "SELECT * FROM user_titles WHERE title_id = ?";
        List<UserTitle> userTitles = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, titleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long userId = resultSet.getLong("user_id");
                    String status = resultSet.getString("status");
                    UserTitle userTitle = new UserTitle(userId, titleId, status);
                    userTitles.add(userTitle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userTitles;
    }
}
