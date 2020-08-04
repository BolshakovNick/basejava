package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<Experience> experienceList = new ArrayList<>();

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection education = (OrganizationSection) o;

        return Objects.equals(experienceList, education.experienceList);
    }

    @Override
    public int hashCode() {
        return experienceList != null ? experienceList.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Experience text : experienceList) {
            sb.append(text.toString()).append('\n');
        }
        return sb.toString();
    }
}
