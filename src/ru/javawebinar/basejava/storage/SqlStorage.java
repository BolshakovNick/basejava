package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        helper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resumes.resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        helper.execute("INSERT INTO resumes.resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            return ps.executeUpdate();
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute("SELECT * FROM resumes.resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute("DELETE FROM resumes.resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.execute("SELECT * FROM resumes.resume r", ps -> {
            List<Resume> result = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                new Resume(rs.getString("uuid"), rs.getString("full_name"));
            }
            return result;
        });
    }

    @Override
    public int size() {
        return helper.execute("SELECT COUNT(*) AS count FROM resumes.resume r", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("SQL exception: can not get count");
            }
            return rs.getInt(1);
        });
    }

    @Override
    public void update(Resume resume) {
        helper.execute("UPDATE resumes.resume r set full_name =? WHERE uuid =?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }
}
