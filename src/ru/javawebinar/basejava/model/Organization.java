package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class Organization extends AbstractSection {
    private final Link homePage;

    private List<Experience> experiences;

    public Organization(Link link, Experience experience) {
        experiences = new ArrayList<>();
        this.homePage = link;
        experiences.add(experience);
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return experiences.equals(that.experiences);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + experiences.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", items=" + experiences +
                '}';
    }
}
