package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Object key, Resume resume);

    protected abstract void doDelete(Object key);

    protected abstract void doUpdate(Object key, Resume resume);

    protected abstract Resume doGet(Object key);

    protected abstract boolean isResumeExist(Object key);

    protected abstract List<Resume> getList();

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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = getList();
        result.sort(Resume::compareTo);
        return result;
    }

    private Object getNotExistedKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (isResumeExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object getExistedKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}
