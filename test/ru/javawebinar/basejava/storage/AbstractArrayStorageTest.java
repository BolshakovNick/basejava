package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Resume r1 = storage.get(UUID_1);
        Resume r2 = storage.get(UUID_2);
        Resume r3 = storage.get(UUID_3);

        Assert.assertEquals("uuid1", r1.getUuid());
        Assert.assertEquals("uuid2", r2.getUuid());
        Assert.assertEquals("uuid3", r3.getUuid());
    }

    @Test (expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
    }

    @Test (expected = ExistStorageException.class)
    public void ResumeAlreadyExists() {
        storage.save(new Resume(UUID_1));
    }

    @Test (expected = StorageException.class)
    public void checkOverflowStorage() {
        while(storage.size() < 10000) {
            try {
                storage.save(new Resume());
            } catch (Exception e) {
                Assert.fail();
            }
        }
        storage.save(new Resume());
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test (expected = NotExistStorageException.class)
    public void ResumeNotExists() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume [] resumes = storage.getAll();
        Assert.assertEquals(storage.size(), resumes.length);
        Assert.assertEquals(storage.get(UUID_1), resumes[0]);
        Assert.assertEquals(storage.get(UUID_2), resumes[1]);
        Assert.assertEquals(storage.get(UUID_3), resumes[2]);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1);
        Assert.assertNotSame(storage.get(UUID_1), resume);
        storage.update(resume);
        Assert.assertSame(storage.get(UUID_1), resume);
    }
}