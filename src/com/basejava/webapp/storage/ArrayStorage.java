package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
    }

    //save the new element after last element
    public void save(Resume resume) {
        if (checkResume(resume) >= 0) {
            System.out.println("Error: storage already has this resume");
            return;
        }
        if (size == storage.length) {
            System.out.println("Exception: Array Index Out Of Bounds");
        }
        storage[size] = resume;
        size++;
    }
    //get the element
    public Resume get(String uuid) {
        if (checkResume(uuid) >=0) {
            return storage[checkResume(uuid)];
        }
        else {
            System.out.println("Error: resume not found");
            return null;
        }
    }

    //delete element and shift other elements one left
    public void delete(String uuid) {
        if (checkResume(uuid) >= 0) {
            storage[checkResume(uuid)] = storage[size-1];
            storage[size-1] = null;
            size--;
            }
        else {
            System.out.println("Error: resume not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size-1);
    }

    //not null element length
    public int size() {
        return size;
    }

    public void update(Resume resume) {
        if(checkResume(resume.getUuid()) >= 0) {
            storage[checkResume(resume)] = resume;
            return;
        }
        System.out.println("Error: resume not found");
    }

    private int checkResume(Object object) {
        String uuid = null;
        if (object instanceof Resume) uuid = ((Resume) object).getUuid();
        if (object instanceof String) uuid = (String)object;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
