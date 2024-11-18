package november13;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;
    private static final String SQL_SELECT_ALL_FROM_DRIVER = "SELECT * FROM driver";
    private static final String SQL_INSERT_DRIVER = "INSERT INTO driver(name, surname, age, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_AGE = "SELECT * FROM driver WHERE age > ?";

    private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM driver WHERE email = ?";
    private static final String SQL_SELECT_BY_PHONE = "SELECT * FROM driver WHERE phone = ?";
    private static final String SQL_SELECT_BY_ADDRESS = "SELECT * FROM driver WHERE address = ?";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"));
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM driver WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_DRIVER)) {
            statement.setString(1, entity.getFirstname());
            statement.setString(2, entity.getLastname());
            statement.setInt(3, entity.getAge());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPhone());
            statement.setString(6, entity.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User entity) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE driver SET name = ?, surname = ?, age = ?, email = ?, phone = ?, address = ? WHERE id = ?")) {
            statement.setString(1, entity.getFirstname());
            statement.setString(2, entity.getLastname());
            statement.setInt(3, entity.getAge());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPhone());
            statement.setString(6, entity.getAddress());
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void remove(User entity) {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM driver WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_AGE)) {
            statement.setInt(1, age);
            ResultSet resultSet = statement.executeQuery();

            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"));
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Optional<User> findByEmail(String email) {
        return findByField(SQL_SELECT_BY_EMAIL, email);
    }

    public Optional<User> findByPhone(String phone) {
        return findByField(SQL_SELECT_BY_PHONE, phone);
    }

    public Optional<User> findByAddress(String address) {
        return findByField(SQL_SELECT_BY_ADDRESS, address);
    }

    private Optional<User> findByField(String query, String value) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
