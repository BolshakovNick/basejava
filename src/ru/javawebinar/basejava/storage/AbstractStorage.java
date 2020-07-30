package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(SK key, Resume resume);

    protected abstract void doDelete(SK key);

    protected abstract void doUpdate(SK key, Resume resume);

    protected abstract Resume doGet(SK key);

    protected abstract boolean isResumeExist(SK key);

    protected abstract List<Resume> getList();

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        String uuid = resume.getUuid();
        doSave(getNotExistedKey(uuid), resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return doGet(getExistedKey(uuid));
    }


    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        doDelete(getExistedKey(uuid));
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        String uuid = resume.getUuid();
        doUpdate(getExistedKey(uuid), resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> result = getList();
        result.sort(Resume::compareTo);
        return result;
    }

    private SK getNotExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (isResumeExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK getExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}
