package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface ReadWriteStrategy {

    void doWrite(Resume resume, FileOutputStream os) throws IOException;

    Resume doRead(FileInputStream is) throws IOException;
}