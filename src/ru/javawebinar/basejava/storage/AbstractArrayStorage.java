package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void saveToStorage(int index, Resume resume);

    protected abstract void deleteFromStorage(int index);

    protected abstract Integer getSearchKey(String uuid);

    @Override
    protected boolean isResumeExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doSave(Object key, Resume resume) {
        int index = (Integer) key;
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveToStorage(index, resume);
        size++;
    }

    @Override
    protected void doDelete(Object key) {
        deleteFromStorage((Integer) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> getList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    protected void doUpdate(Object key, Resume resume) {
        storage[(Integer) key] = resume;
    }

    @Override
    protected Resume doGet(Object key) {
        return storage[(Integer) key];
    }
}
