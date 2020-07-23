package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getKey(String uuid);

    protected abstract void doSave(Object key, Resume resume);

    protected abstract void doDelete(Object key, String uuid);

    protected abstract void doUpdate(Object key, Resume resume);

    protected abstract Resume doGet(Object key, String uuid);

    @Override
    public void save(Resume resume) {
        Object key = getKey(resume.getUuid());
        doSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getKey(uuid);
        return doGet(key, uuid);
    }


    @Override
    public void delete(String uuid) {
        Object key = getKey(uuid);
        doDelete(key, uuid);
    }

    @Override
    public void update(Resume resume) {
        Object key = getKey(resume.getUuid());
        doUpdate(key, resume);
    }
}
