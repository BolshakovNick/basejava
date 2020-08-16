package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataSerializationStrategy implements SerializeStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();

                if (sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)) {
                    dos.writeUTF(sectionType.name());
                    dos.writeUTF(section.toString());
                } else if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
                    dos.writeUTF(sectionType.name());
                    List<String> list = ((MarkingListSection) section).getMarkingLines();
                    writeCollection(dos, list, DataOutputStream::writeUTF);
                } else if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)) {
                    dos.writeUTF(sectionType.name());
                    List<Organization> orgList = ((OrganizationSection) section).getOrganizations();

                    writeCollection(dos, orgList, (dos12, element) -> {
                        writeLink(element.getHomePage(), dos12);
                        writeCollection(dos12, element.getPositions(), (dos1, element1) -> writePosition(element1, dos1));
                    });
                }
            }
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
                    List<String> markingLines = new ArrayList<>();
                    int localSize = dis.readInt();
                    for (int j = 0; j < localSize; j++) {
                        markingLines.add(dis.readUTF());
                    }
                    resume.addSection(type, new MarkingListSection(markingLines));
                } else if (type.equals(SectionType.EXPERIENCE) || type.equals(SectionType.EDUCATION)) {
                    List<Organization> orgList = new ArrayList<>();
                    int localSize = dis.readInt();
                    for (int k = 0; k < localSize; k++) {
                        Link link = readLink(dis);
                        List<Organization.Position> posList = new ArrayList<>();
                        int localSize2 = dis.readInt();
                        for (int m = 0; m < localSize2; m++) {
                            posList.add(readPosition(dis));
                        }
                        orgList.add(new Organization(link, posList));
                    }
                    resume.addSection(type, new OrganizationSection(orgList));
                }
            }
        }
        return resume;
    }

    private void writeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getName());
        String url = link.getUrl();
        if (url == null) {
            dos.writeUTF("null");
        } else {
            dos.writeUTF(url);
        }
    }

    private void writePosition(Organization.Position position, DataOutputStream dos) throws IOException {
        writeLocalDate(position.getStartDate(), dos);
        writeLocalDate(position.getEndDate(), dos);
        dos.writeUTF(position.getTitle());
        String description = position.getDescription();
        if (description == null) {
            dos.writeUTF("null");
        } else {
            dos.writeUTF(description);
        }
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

    interface Readable<T> {
        T readElement(DataInputStream dis) throws IOException;
    }

    interface Writable<T> {
        void writeElement(DataOutputStream dos, T element) throws IOException;
    }

    private static <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writable<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.writeElement(dos, element);
        }
    }

    private static <T> List<T> readList(DataInputStream dis, Readable<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(reader.readElement(dis));
        }
        return result;
    }

    /*private <T> void writeList(List<T> list, DataOutputStream dos) throws IOException {
        for (T element : list) {
            writeElement(element, dos);
        }
    }

    private <T> void writeElement(T element, DataOutputStream dos) throws IOException {
        if (element instanceof Link) {
            writeLink((Link)element, dos);
        } else if (element instanceof Organization.Position) {
            writePosition((Organization.Position) element, dos);
        } else if (element instanceof LocalDate) {
            writeLocalDate((LocalDate) element, dos);
        } else if (element instanceof String) {
            dos.writeUTF((String) element);
        }
    }*/
}