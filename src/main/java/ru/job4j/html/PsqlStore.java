package ru.job4j.html;

import ru.job4j.model.Post;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection connection;

    public PsqlStore(Properties cfg) {
        initConnection(cfg);
    }

    public void initConnection(Properties cfg) {
        var url = "jdbc:postgresql://localhost:5432/rabbit_db";
        var login = "postgres";
        var password = "toor";
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void save(Post post) {
        var query = "insert into posts(id, name, text, link, date) values (?, ?, ?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setLong(1, post.getId());
            statement.setString(2, post.getName());
            statement.setString(3, post.getText());
            statement.setString(4, post.getLink());
            statement.setString(5, post.getCreated());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        var result = new ArrayList<Post>();
        var query = "select * from posts";
        try (var statement = connection.prepareStatement(query)) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new Post(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getString("data")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Post findById(Long id) {
        var result = new Post();
        var query = "select * from post where id = ?";
        try (var statement = connection.prepareStatement(query)) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.setId(resultSet.getLong("id"));
                    result.setName(resultSet.getString("name"));
                    result.setText(resultSet.getString("text"));
                    result.setLink(resultSet.getString("link"));
                    result.setCreated(resultSet.getString("data"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
