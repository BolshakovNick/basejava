package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.XmlSerializationStrategy;

public class XmlSerializationPathStorageTest extends AbstractStorageTest {

    public XmlSerializationPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlSerializationStrategy()));
    }
}