package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isResumeExist(Object key) {
        return storage.containsValue(key);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doSave(Object key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get(((Resume)key).getUuid());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> resumes = new ArrayList<>(storage.values());
        resumes.sort(Resume::compareTo);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
