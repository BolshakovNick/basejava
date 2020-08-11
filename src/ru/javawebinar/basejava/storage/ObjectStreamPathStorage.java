package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    private ReadWriteStrategy strategy;

    protected ObjectStreamPathStorage(String dir, ReadWriteStrategy strategy) {
        super(dir);
        this.strategy = strategy;
    }

    @Override
    protected void doWrite(Resume resume, FileOutputStream os) throws IOException {
        strategy.doWrite(resume, os);
    }

    @Override
    protected Resume doRead(FileInputStream is) throws IOException {
        return strategy.doRead(is);
    }
}
