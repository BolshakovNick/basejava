package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class StandardSerializationStrategy implements ReadWriteStrategy {
    @Override
    public void doWrite(Resume resume, FileOutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(FileInputStream is) throws IOException {
        try (ObjectInputStream oos = new ObjectInputStream(is)) {
            return (Resume) oos.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
