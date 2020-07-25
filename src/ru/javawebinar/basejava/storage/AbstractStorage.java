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

    protected Object checkExistResume(String uuid) {
        Object key = getKey(uuid);
        if (isResumeExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected Object checkNotExistResume(String uuid) {
        Object key = getKey(uuid);
        if (!isResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        doSave(checkExistResume(uuid), resume);
    }

    @Override
    public Resume get(String uuid) {
        return doGet(checkNotExistResume(uuid));
    }


    @Override
    public void delete(String uuid) {
        doDelete(checkNotExistResume(uuid));
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        doUpdate(checkNotExistResume(uuid), resume);
    }

}
