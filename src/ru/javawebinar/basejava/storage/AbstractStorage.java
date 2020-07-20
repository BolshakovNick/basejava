package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected int size = 0;

    protected abstract Resume[] getResumesArray();

    protected abstract int getIndex(String uuid);

    protected abstract void doSave(int index, Resume resume);

    protected abstract void doDelete(int index);

    protected abstract void doUpdate(int index, Resume resume);

    protected abstract Resume doGet(int index);

    @Override
    public int size() {
        return size;
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(index, resume);
        size++;
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(index);
    }


    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(index);
            size--;
        }

    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            doUpdate(index, resume);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return getResumesArray();
    }
}
