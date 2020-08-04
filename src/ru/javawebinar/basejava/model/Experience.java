package ru.javawebinar.basejava.model;

import java.net.URL;
import java.time.YearMonth;
import java.util.Objects;

public class Experience extends AbstractSection {
    private URL link;
    private YearMonth from;
    private YearMonth to;
    private String text;

    public Experience(URL link, YearMonth from, YearMonth to, String text) {
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

    public YearMonth getFrom() {
        return from;
    }

    public void setFrom(YearMonth from) {
        this.from = from;
    }

    public YearMonth getTo() {
        return to;
    }

    public void setTo(YearMonth to) {
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

        Experience that = (Experience) o;

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
