package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getKey(String uuid);

    protected abstract void doSave(Object key, Resume resume);

    protected abstract void doDelete(Object key);

    protected abstract void doUpdate(Object key, Resume resume);

    protected abstract Resume doGet(Object key);

    protected abstract boolean isResumeExist(Object key);

    protected void checkExistResume(Object key, String uuid) {
        if (isResumeExist(key)) {
            throw new ExistStorageException(uuid);
        }
    }

    protected void checkNotExistResume(Object key, String uuid) {
        if (!isResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object key = getKey(uuid);
        checkExistResume(key, uuid);
        doSave(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getKey(uuid);
        checkNotExistResume(key, uuid);
        return doGet(key);
    }


    @Override
    public void delete(String uuid) {
        Object key = getKey(uuid);
        checkNotExistResume(key, uuid);
        doDelete(key);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object key = getKey(uuid);
        checkNotExistResume(key, uuid);
        doUpdate(key, resume);
    }

}
