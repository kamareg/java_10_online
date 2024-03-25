package ua.com.alevel.db.impl.mysql;

import ua.com.alevel.config.JdbcService;
import ua.com.alevel.db.AuthorDb;
import ua.com.alevel.entity.Author;
import ua.com.alevel.factory.ObjectFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class MySqlAuthorDbImpl implements AuthorDb {

    private final Connection connection = ObjectFactory.getInstance().getService(JdbcService.class).getConnection();

    @Override
    public void create(Author author) {
        try (PreparedStatement ps = connection.prepareStatement("insert into authors values (UUID(), ?, ?)")) {
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("create not working: " + e.getMessage());
        }
    }

    @Override
    public void update(Author author) {
        try (PreparedStatement ps = connection.prepareStatement("update authors set first_name = ?, last_name = ? where id = ?")) {
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setString(3, author.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update not working: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from authors where id = ?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete not working: " + e.getMessage());
        }
    }

    public boolean existById(String id) {
        try (PreparedStatement ps = connection.prepareStatement("select count(id) as count from authors where id = ?")) {
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return resultSet.getLong("count") == 1;
            }
        } catch (SQLException e) {
            System.out.println("existById not working: " + e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public Optional<Author> findById(String id) {
        if (!existById(id)) {
            return Optional.empty();
        }
        try (PreparedStatement ps = connection.prepareStatement("select * from authors where id = ?")) {
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return Optional.of(buildAuthorByResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("findById not working: " + e.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public ArrayList<Author> findAll() {
        ArrayList<Author> authors = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from authors")
        ) {
            while (resultSet.next()) {
                authors.add(buildAuthorByResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("findAll not working: " + e.getMessage());
            return new ArrayList<>();
        }
        return authors;
    }

    private Author buildAuthorByResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return author;
    }
}
