package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Object getKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    protected int getIndex(Object key) {
        return (Integer) key;
    }

    @Override
    protected void doSave(Object key, Resume resume) {
        int index = getIndex(key);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        storage.add(resume);
    }

    @Override
    protected void doDelete(Object key, String uuid) {
        int index = getIndex(key);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        storage.remove(index);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        int index = getIndex(key);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage.set(index, resume);
    }

    @Override
    protected Resume doGet(Object key, String uuid) {
        int index = getIndex(key);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
