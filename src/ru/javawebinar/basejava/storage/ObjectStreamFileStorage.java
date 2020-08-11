package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ObjectStreamFileStorage extends AbstractFileStorage {
    private ReadWriteStrategy strategy;

    protected ObjectStreamFileStorage(File directory, ReadWriteStrategy strategy) {
        super(directory);
        this.strategy = strategy;
    }

    public ReadWriteStrategy getStrategy() {
        return strategy;
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
