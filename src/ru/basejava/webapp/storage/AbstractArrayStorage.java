package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void doSave(int index, Resume resume);

    protected abstract void doDelete(int index);

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Error: resume with uuid [" + uuid + "] not found");
        return null;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("Error: storage already has this resume with uuid [" + resume.getUuid() + "]");
            return;
        }
        if (size == storage.length) {
            System.out.println("Exception: Array Index Out Of Bounds");
            return;
        }
        doSave(index, resume);
        size++;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            doDelete(index);
            size--;
        } else {
            System.out.println("Error: resume with uuid [" + uuid + "] not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            return;
        }
        System.out.println("Error: resume with uuid [" + resume.getUuid() + "] not found");
    }
}
