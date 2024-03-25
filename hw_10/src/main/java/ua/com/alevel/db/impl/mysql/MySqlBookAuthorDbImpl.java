package ua.com.alevel.db.impl.mysql;

import ua.com.alevel.config.JdbcService;
import ua.com.alevel.db.BookAuthorDb;
import ua.com.alevel.entity.BookAuthor;
import ua.com.alevel.factory.ObjectFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class MySqlBookAuthorDbImpl implements BookAuthorDb {
    private final Connection connection = ObjectFactory.getInstance().getService(JdbcService.class).getConnection();

    @Override
    public void create(BookAuthor bookAuthor) {
        try (PreparedStatement ps = connection.prepareStatement("insert into book_author values (?, ?)")) {
            ps.setString(1, bookAuthor.getBookId());
            ps.setString(2, bookAuthor.getAuthorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("create not working: " + e.getMessage());
        }
    }

    @Override
    public void update(BookAuthor bookAuthor) {
        try (PreparedStatement ps = connection.prepareStatement("delete from book_author where book_id = ? and author_id = ?")) {
            ps.setString(1, bookAuthor.getBookId());
            ps.setString(2, bookAuthor.getAuthorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("update not working: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try (PreparedStatement ps = connection.prepareStatement("delete from book_author where book_id = ? or author_id = ?")) {
            ps.setString(1, id);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete not working: " + e.getMessage());
        }
    }

    @Override
    public Optional<BookAuthor> findById(String id) {
        //placeholder
        return Optional.empty();
    }

    @Override
    public ArrayList<BookAuthor> findAll() {
        ArrayList<BookAuthor> bookAuthors = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from book_author")
        ) {
            while (resultSet.next()) {
                bookAuthors.add(buildBookAuthorByResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("findAll not working: " + e.getMessage());
            return new ArrayList<>();
        }
        return bookAuthors;
    }

    private BookAuthor buildBookAuthorByResultSet(ResultSet resultSet) throws SQLException {
        String book_id = resultSet.getString("book_id");
        String author_id = resultSet.getString("author_id");
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setBookId(book_id);
        bookAuthor.setAuthorId(author_id);
        return bookAuthor;
    }
}
