package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Object key, Resume resume) {
        if (storage.containsKey(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
        storage.put((String) key, resume);
    }

    @Override
    protected void doDelete(Object key, String uuid) {
        if (!storage.containsKey(key)) {
            throw new NotExistStorageException(uuid);
        }
        storage.remove(key);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        if (!storage.containsKey(key)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage.put((String) key, resume);
    }

    @Override
    protected Resume doGet(Object key, String uuid) {
        if (!storage.containsKey(key)) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
