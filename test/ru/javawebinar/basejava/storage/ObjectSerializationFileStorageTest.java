package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.ObjectSerializationStrategy;

public class ObjectSerializationFileStorageTest extends AbstractStorageTest {

    public ObjectSerializationFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectSerializationStrategy()));
    }
}