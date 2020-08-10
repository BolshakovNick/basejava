package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface SourceStrategy<S> {

    S getSearchKey(String uuid);

    void doSave(S source, Resume resume);

    void doDelete(S source);

    void doUpdate(S source, Resume resume);

    Resume doGet(S source);

    boolean isExist(S source);

    List<Resume> getList();

    void clear();

    int size();
}
