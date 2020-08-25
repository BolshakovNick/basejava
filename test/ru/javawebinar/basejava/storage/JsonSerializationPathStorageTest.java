package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.JsonSerializationStrategy;

public class JsonSerializationPathStorageTest extends AbstractStorageTest {

    public JsonSerializationPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonSerializationStrategy()));
    }
}