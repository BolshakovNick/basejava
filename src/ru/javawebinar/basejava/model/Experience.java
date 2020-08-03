package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Experience extends AbstractSection {
    private List<LinkDateTextSection> workList = new ArrayList<>();

    public List<LinkDateTextSection> getWorkList() {
        return workList;
    }

    public void setWorkList(List<LinkDateTextSection> workList) {
        this.workList = workList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        return Objects.equals(workList, that.workList);
    }

    @Override
    public int hashCode() {
        return workList != null ? workList.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LinkDateTextSection text : workList) {
            sb.append(text.toString()).append('\n');
        }
        return sb.toString();
    }
}
