package ua.com.alevel.db.impl.mysql;

import ua.com.alevel.config.JdbcService;
import ua.com.alevel.db.BookDb;
import ua.com.alevel.entity.Book;
import ua.com.alevel.factory.ObjectFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class MySqlBookDbImpl implements BookDb {

    private final Connection connection = ObjectFactory.getInstance().getService(JdbcService.class).getConnection();

    @Override
    public void create(Book book) {
        try (PreparedStatement ps = connection.prepareStatement("insert into books values (UUID(), ?, ?)")) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getYear());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("create not working: " + e.getMessage());
        }
    }

    @Override
    public void update(Book book) {
        try (PreparedStatement ps = connection.prepareStatement("update books set title = ?, year = ? where id = ?")) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getYear());
            ps.setString(3, book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update not working: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from books where id = ?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete not working: " + e.getMessage());
        }
    }

    public boolean existById(String id) {
        try (PreparedStatement ps = connection.prepareStatement("select count(id) as count from books where id = ?")) {
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
    public Optional<Book> findById(String id) {
        if (!existById(id)) {
            return Optional.empty();
        }
        try (PreparedStatement ps = connection.prepareStatement("select * from books where id = ?")) {
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return Optional.of(buildBookByResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("findById not working: " + e.getMessage());
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public ArrayList<Book> findAll() {
        ArrayList<Book> books = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from books")
        ) {
            while (resultSet.next()) {
                books.add(buildBookByResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("findAll not working: " + e.getMessage());
            return new ArrayList<>();
        }
        return books;
    }

    private Book buildBookByResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String title = resultSet.getString("title");
        int year = resultSet.getInt("year");
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setYear(year);
        return book;
    }
}
