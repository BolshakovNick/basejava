package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Education extends AbstractSection {
    private List<LinkDateTextSection> educationList = new ArrayList<>();

    public List<LinkDateTextSection> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<LinkDateTextSection> educationList) {
        this.educationList = educationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Education education = (Education) o;

        return Objects.equals(educationList, education.educationList);
    }

    @Override
    public int hashCode() {
        return educationList != null ? educationList.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LinkDateTextSection text : educationList) {
            sb.append(text.toString()).append('\n');
        }
        return sb.toString();
    }
}
