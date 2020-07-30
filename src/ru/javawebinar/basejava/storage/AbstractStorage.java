package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(SK key, Resume resume);

    protected abstract void doDelete(SK key);

    protected abstract void doUpdate(SK key, Resume resume);

    protected abstract Resume doGet(SK key);

    protected abstract boolean isResumeExist(SK key);

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

    private SK getNotExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (isResumeExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK getExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}
