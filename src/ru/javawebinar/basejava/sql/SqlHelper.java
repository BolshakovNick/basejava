package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sqlCommand, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            return executor.doExecute(ps);
        } catch (SQLException e) {
            throw new ExistStorageException(e.getMessage());
        }
    }
}