package ru.job4j.model;

import java.util.Date;
import java.util.Objects;

public class Post {
    private long id;
    private String name;
    private String text;
    private String link;
    private Date created;

    public Post() {
    }

    public Post(long id, String name, String text, String link, Date created) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.link = link;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                link == post.link &&
                Objects.equals(name, post.name) &&
                Objects.equals(text, post.text) &&
                Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text, link, created);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", link=" + link +
                ", created=" + created +
                '}';
    }
}
