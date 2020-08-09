package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarkingListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<String> markingLines;

    public MarkingListSection() {
        this.markingLines = new ArrayList<>();
    }

    public MarkingListSection(List<String> markingLines) {
        this.markingLines = markingLines;
    }

    public MarkingListSection(String... markingLines) {
        this(Arrays.asList(markingLines));
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

        return markingLines.equals(that.markingLines);
    }

    @Override
    public int hashCode() {
        return markingLines.hashCode();
    }
}