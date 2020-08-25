package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.DataSerializationStrategy;

public class DataSerializationPathStorageTest extends AbstractStorageTest {

    public DataSerializationPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataSerializationStrategy()));
    }
}