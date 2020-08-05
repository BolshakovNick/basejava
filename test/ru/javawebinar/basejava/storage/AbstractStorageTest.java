package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.testResume(UUID_1, "Steve King");
        RESUME_2 = ResumeTestData.testResume(UUID_2, "Marry Poppins");
        RESUME_3 = ResumeTestData.testResume(UUID_3, "Ivan Ivanov");
        RESUME_4 = ResumeTestData.testResume(UUID_4, "Petr Petrov");
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        try {
            storage.delete(UUID_1);
            Assert.assertEquals(2, storage.size());
        } catch (NotExistStorageException exception) {
            Assert.fail("Exception in block try - Method delete()");
        }
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "new name");
        storage.update(resume);
        Assert.assertSame(storage.get(UUID_1), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = storage.getAllSorted();
        Assert.assertEquals(storage.size(), resumes.size());
        Assert.assertEquals(Arrays.asList(RESUME_3, RESUME_2, RESUME_1), resumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Resume r4 = new Resume("uuid4", "random name");
        storage.save(r4);
        Assert.assertEquals(r4, storage.get("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}