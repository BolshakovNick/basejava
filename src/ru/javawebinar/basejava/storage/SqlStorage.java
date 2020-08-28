package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, entry.getKey().name());
                    ps.setString(3, entry.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute("" +
                "   SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "       ON r.uuid=c.resume_uuid " +
                "    WHERE r.uuid =? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.addContact(type, value);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.execute("" +
                "SELECT * FROM resume " +
                "LEFT JOIN contact c " +
                "ON resume.uuid = c.resume_uuid " +
                "ORDER BY full_name, uuid", ps -> {
            List<Resume> result = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = isExist(result, uuid);
                if (resume == null) {
                    resume = new Resume(uuid, rs.getString("full_name"));
                    result.add(resume);
                }
                resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            return result;
        });
    }

    private Resume isExist(List<Resume> list, String uuid) {
        for (Resume r : list) {
            if (uuid.equals(r.getUuid())) {
                return r;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return helper.execute("SELECT COUNT(*) AS count FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        });
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name =? WHERE uuid =?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                ps.execute();
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT resume_uuid FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || rs.getString("resume_uuid") == null) {
                    for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                        try (PreparedStatement ps1 = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                            ps1.setString(1, resume.getUuid());
                            ps1.setString(2, entry.getKey().name());
                            ps1.setString(3, entry.getValue());
                        }
                    }
                } else {
                    for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                        try (PreparedStatement ps2 = conn.prepareStatement("UPDATE contact c SET (type, value) = (?, ?) WHERE resume_uuid = ?")) {
                            ps2.setString(1, entry.getKey().name());
                            ps2.setString(2, entry.getValue());
                            ps2.setString(3, resume.getUuid());
                        }
                    }
                }
            }
            return null;
        });
    }
}
