package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isResumeExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected Object getKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void doSave(Object key, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void doDelete(Object key) {
        int index = (Integer) key;
        storage.remove(index);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        storage.set((Integer) key, resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
