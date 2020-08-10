package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> implements SourceStrategy<File>, ReadWriteInterface{
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(resume, new FileOutputStream(file));
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    public void doDelete(File file) {
        if (!isExist(file)) {
            throw new StorageException("File delete error", file.getName());
        }
        file.delete();
    }

    @Override
    public void doUpdate(File file, Resume resume) {
        try {
            doWrite(resume, new FileOutputStream(file));
        } catch (IOException e) {
            throw new StorageException("File write error ", resume.getUuid(), e);
        }
    }

    @Override
    public Resume doGet(File file){
        try {
            return doRead(new FileInputStream(file));
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
        File [] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> result = new ArrayList<>(files.length);
        for (File file : files) {
            result.add(doGet(file));
        }
        return result;
    }

    @Override
    public void clear() {
        File [] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list(), "null directory does not have size").length;
    }
}
