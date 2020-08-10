package ru.javawebinar.basejava.storage;

import java.io.File;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamStorage(new File(STORAGE_DIR)));
    }
}