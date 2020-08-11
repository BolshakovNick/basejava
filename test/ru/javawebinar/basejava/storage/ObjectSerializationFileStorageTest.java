package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.ObjectSerializationStrategy;

import java.io.File;

public class ObjectSerializationFileStorageTest extends AbstractStorageTest {

    public ObjectSerializationFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectSerializationStrategy()));
    }
}