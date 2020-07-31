package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.ResumeContentException;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LinkContent extends Content {
    Map<URL, String> linkContent;

    public LinkContent() {
        this.linkContent = new HashMap<>();
    }

    public Map<URL, String> getLinkContent() {
        return linkContent;
    }

    public void add(URL url, String text) throws ResumeContentException {
        if (linkContent.containsKey(url))
            throw new ResumeContentException("Adding is not available: URL [" + url + "] already exists");
        linkContent.put(url, text);
    }

    public void remove(URL url) throws ResumeContentException {
        if (!linkContent.containsKey(url))
            throw new ResumeContentException("Removing is not available: URL [" + url + "] not exists");
        linkContent.remove(url);
    }

    public void update(URL url, String text) throws ResumeContentException {
        if (!linkContent.containsKey(url))
            throw new ResumeContentException("Updating is not available: URL [" + url + "] not exists");
        linkContent.put(url, text);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<URL, String> pair : linkContent.entrySet()) {
           builder.append(pair.getKey().toString()).append('\n').append(pair.getValue());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkContent that = (LinkContent) o;

        return Objects.equals(linkContent, that.linkContent);
    }

    @Override
    public int hashCode() {
        return linkContent != null ? linkContent.hashCode() : 0;
    }
}
