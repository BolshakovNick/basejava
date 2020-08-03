package ru.javawebinar.basejava.model;

import java.net.URL;
import java.util.Date;
import java.util.Objects;

public class LinkDateTextSection extends AbstractSection {
    private URL link;
    private Date from;
    private Date to;
    private String text;

    public LinkDateTextSection(URL link, Date from, Date to, String text) {
        this.link = link;
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkDateTextSection that = (LinkDateTextSection) o;

        if (!Objects.equals(link, that.link)) return false;
        if (!Objects.equals(from, that.from)) return false;
        if (!Objects.equals(to, that.to)) return false;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        int result = link != null ? link.hashCode() : 0;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return link + "\n" + from + " - " + to + "\t" + text;
    }
}
