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
        index = Math.abs(index) - 1;
        System.arraycopy(storage, index, storage, index+1, size-index);
        storage[index] = resume;
    }

    @Override
    protected void doDelete(int index) {
        System.arraycopy(storage, index+1, storage, index, size-index);
    }
}
