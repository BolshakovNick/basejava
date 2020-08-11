package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.ObjectSerializationStrategy;

import static org.junit.Assert.*;

public class ObjectSerializationPathStorageTest extends AbstractStorageTest {

    public ObjectSerializationPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectSerializationStrategy()));
    }
}