package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void doSave(int index, Resume resume) {
        storage[Math.abs(index) - 1] = resume;
    }

    @Override
    protected void doDelete(int index) {
        for (int i = index; i < size; i++) {
            if ((i + 1) < storage.length) storage[i] = storage[i + 1]; //check index out of bound
            else storage[i] = null;
        }
    }
}
