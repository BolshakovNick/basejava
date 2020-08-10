package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.List;

public class ObjectStreamStorage extends AbstractStorage implements ReadWriteInterface {
    SourceStrategy strategy;

    public ObjectStreamStorage(File source) {
        this.strategy = new AbstractFileStorage(source) {
            @Override
            public void doWrite(Resume resume, OutputStream os) throws IOException {
                ObjectStreamStorage.this.doWrite(resume, os);
            }

            @Override
            public Resume doRead(InputStream is) throws IOException {
                return ObjectStreamStorage.this.doRead(is);
            }
        };
    }

    public ObjectStreamStorage(String source) {
        this.strategy = new AbstractPathStorage(source) {
            @Override
            public void doWrite(Resume resume, OutputStream os) throws IOException {
                ObjectStreamStorage.this.doWrite(resume, os);
            }

            @Override
            public Resume doRead(InputStream is) throws IOException {
                return ObjectStreamStorage.this.doRead(is);
            }
        };
    }

    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream oos = new ObjectInputStream(is)) {
            return (Resume) oos.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return strategy.getSearchKey(uuid);
    }

    @Override
    protected void doSave(Object key, Resume resume) {
        strategy.doSave(key, resume);
    }

    @Override
    protected void doDelete(Object key) {
        strategy.doDelete(key);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        strategy.doUpdate(key, resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return strategy.doGet(key);
    }

    @Override
    protected boolean isExist(Object key) {
        return strategy.isExist(key);
    }

    @Override
    protected List<Resume> getList() {
        return strategy.getList();
    }

    @Override
    public void clear() {
        strategy.clear();
    }

    @Override
    public int size() {
        return strategy.size();
    }
}