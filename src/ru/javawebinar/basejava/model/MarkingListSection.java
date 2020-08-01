package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.ResumeContentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarkingListSection extends AbstractSection {
    private List<String> markingLines;

    public MarkingListSection() {
        this.markingLines = new ArrayList<>();
    }

    public List<String> getMarkingLines() {
        return markingLines;
    }

    public void add(String line) throws ResumeContentException {
        if (markingLines.contains(line))
            throw new ResumeContentException("Adding is not available: Text [" + line + "] already exists");
        markingLines.add(line);
    }

    public void remove(String line) throws ResumeContentException {
        if (!markingLines.contains(line))
            throw new ResumeContentException("Removing is not available: Text [" + line + "] not exists");
        markingLines.remove(line);
    }

    public void update(int index, String line) throws ResumeContentException {
        if (!markingLines.contains(line))
            throw new ResumeContentException("Updating is not available: line [" + line + "] not exists");
        markingLines.set(index, line);
    }

    public int getIndex(String line) {
        return markingLines.indexOf(line);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String line : markingLines) {
            builder.append(line).append('\n');
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkingListSection that = (MarkingListSection) o;

        return Objects.equals(markingLines, that.markingLines);
    }

    @Override
    public int hashCode() {
        return markingLines != null ? markingLines.hashCode() : 0;
    }
}
