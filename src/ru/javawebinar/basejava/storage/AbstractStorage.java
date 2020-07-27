package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Object key, Resume resume);

    protected abstract void doDelete(Object key);

    protected abstract void doUpdate(Object key, Resume resume);

    protected abstract Resume doGet(Object key);

    protected abstract boolean isResumeExist(Object key);

    protected Object getNotExistedKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (isResumeExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected Object getExistedKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        doSave(getNotExistedKey(uuid), resume);
    }

    @Override
    public Resume get(String uuid) {
        return doGet(getExistedKey(uuid));
    }


    @Override
    public void delete(String uuid) {
        doDelete(getExistedKey(uuid));
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        doUpdate(getExistedKey(uuid) , resume);
    }

}
