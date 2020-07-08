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
        Arrays.fill(storage, null);
    }

    //save the new element after last element
    public void save(Resume r) {
        for (int i = 0; i < size; i++) {
            if (storage[i] == r) {
                System.out.println("Error: storage already has this resume");
                return;
            }
            if (size == storage.length) {
                System.out.println("Exception: Array Index Out Of Bounds");
            }
        }
        storage[size] = r;
        size++;
    }
    //get the element
    public Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                resume = storage[i];
                break;
            }
            else {
                System.out.println("Error: storage has not resume with this uuid");
            }
        }
        return resume;
    }

    //delete element and shift other elements one left
    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size-1];
                storage[size-1] = null;
                size--;
            }
            else {
                System.out.println("Error: storage has not resume with this uuid");
            }
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
        for (int i = 0; i < size; i++) {
            if(storage[i] == resume)
                System.out.println("Error: storage already has this resume");
        }
    }
}
