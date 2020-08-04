package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doSave(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume key) {
        storage.remove((key).getUuid());
    }

    @Override
    protected void doUpdate(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume key) {
        return key;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
