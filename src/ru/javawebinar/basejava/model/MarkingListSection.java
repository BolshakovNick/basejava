package ru.javawebinar.basejava.model;

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
