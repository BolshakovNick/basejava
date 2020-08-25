package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.ObjectSerializationStrategy;

public class ObjectSerializationPathStorageTest extends AbstractStorageTest {

    public ObjectSerializationPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectSerializationStrategy()));
    }
}