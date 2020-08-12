package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.ReadWriteStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private ReadWriteStrategy strategy;

    protected FileStorage(File directory, ReadWriteStrategy strategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    public File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    public void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    public void doUpdate(File file, Resume resume) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error ", resume.getUuid(), e);
        }
    }

    @Override
    public Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }


    @Override
    public boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public List<Resume> getList() {
        File[] files = getListFiles();
        List<Resume> result = new ArrayList<>(files.length);
        for (File file : files) {
            result.add(doGet(file));
        }
        return result;
    }

    @Override
    public void clear() {
        for (File file : getListFiles()) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    private File[] getListFiles() {
        File[] result = directory.listFiles();
        if (result == null) {
            throw new StorageException("File list must not be null");
        }
        return result;
    }
}