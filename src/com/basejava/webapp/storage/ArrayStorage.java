package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage{
    public static final int STORAGE_LIMIT = 10000;
    private Resume[] storage = new Resume[STORAGE_LIMIT ];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    //save the new element after last element
    public void save(Resume resume) {
        if (checkResume(resume.getUuid()) >= 0) {
            System.out.println("Error: storage already has this resume with uuid [" + resume.getUuid() + "]");
            return;
        }
        if (size == storage.length) {
            System.out.println("Exception: Array Index Out Of Bounds");
            return;
        }
        storage[size] = resume;
        size++;
    }

    //get the element
    public Resume get(String uuid) {
        int index = checkResume(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Error: resume with uuid [" + uuid + "] not found");
        return null;
    }

    //delete element and shift other elements one left
    public void delete(String uuid) {
        int index = checkResume(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
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

    //not null element length
    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int index = checkResume(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            return;
        }
        System.out.println("Error: resume with uuid [" + resume.getUuid() + "] not found");
    }

    private int checkResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
