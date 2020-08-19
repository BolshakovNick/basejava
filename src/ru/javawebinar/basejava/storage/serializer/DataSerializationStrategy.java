package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DataSerializationStrategy implements SerializeStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), (entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeCollection(dos, sections.entrySet(), (entry) -> {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(sectionType.name());
                        dos.writeUTF(((SimpleTextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        dos.writeUTF(sectionType.name());
                        List<String> list = ((MarkingListSection) section).getMarkingLines();
                        writeCollection(dos, list, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        dos.writeUTF(sectionType.name());
                        List<Organization> orgList = ((OrganizationSection) section).getOrganizations();
                        writeCollection(dos, orgList, (element) -> {
                            writeLink(element.getHomePage(), dos);
                            writeCollection(dos, element.getPositions(), (element1) -> writePosition(element1, dos));
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        Resume resume;
        try (DataInputStream dis = new DataInputStream(is)) {
            resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                if (type.equals(SectionType.PERSONAL) || type.equals(SectionType.OBJECTIVE)) {
                    SimpleTextSection text = new SimpleTextSection(dis.readUTF());
                    resume.addSection(type, text);
                } else if (type.equals(SectionType.ACHIEVEMENT) || type.equals(SectionType.QUALIFICATIONS)) {
                    resume.addSection(type, new MarkingListSection(readList(dis, dis::readUTF)));
                } else if (type.equals(SectionType.EXPERIENCE) || type.equals(SectionType.EDUCATION)) {
                    List<Organization> orgList = readList(dis, () -> new Organization(readLink(dis), readList(dis, () -> readPosition(dis))));
                    resume.addSection(type, new OrganizationSection(orgList));
                }
            }
        }
        return resume;
    }

    private void writeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getName());
        String url = link.getUrl();
        dos.writeUTF(url == null ? "null" : url);
    }

    private void writePosition(Organization.Position position, DataOutputStream dos) throws IOException {
        writeLocalDate(position.getStartDate(), dos);
        writeLocalDate(position.getEndDate(), dos);
        dos.writeUTF(position.getTitle());
        String description = position.getDescription();
        dos.writeUTF(description == null ? "null" : description);
    }

    private void writeLocalDate(LocalDate date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
    }

    private Link readLink(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String url = dis.readUTF();
        return url.equals("null") ? new Link(name, null) : new Link(name, url);
    }

    private Organization.Position readPosition(DataInputStream dis) throws IOException {
        LocalDate startDate = readLocalDate(dis);
        LocalDate finishDate = readLocalDate(dis);
        String title = dis.readUTF();
        String description = dis.readUTF();
        return description.equals("null") ? new Organization.Position(startDate, finishDate, title, null) : new Organization.Position(startDate, finishDate, title, description);
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
    }

    interface Writable<T> {
        void writeElement(T element) throws IOException;
    }

    private static <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writable<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.writeElement(element);
        }
    }

    interface Readable<T> {
        T readElement() throws IOException;
    }

    private static <T> List<T> readList(DataInputStream dis, Readable<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(reader.readElement());
        }
        return result;
    }
}