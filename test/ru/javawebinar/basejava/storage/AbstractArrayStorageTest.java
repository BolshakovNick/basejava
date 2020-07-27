package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void checkOverflowStorage() {
        int i = 1;
        while (storage.size() < AbstractArrayStorage.STORAGE_LIMIT) {
            try {
                storage.save(new Resume("random Name" + i));
                i++;
            } catch (StorageException exception) {
                Assert.fail("Exception in block try - Method checkOverflowStorage()");
            }
        }
        storage.save(new Resume("random name" + i));
    }
}