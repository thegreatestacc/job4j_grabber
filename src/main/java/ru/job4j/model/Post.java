package ru.job4j.model;

import java.util.Date;
import java.util.Objects;

public class Post {
    private long id;
    private String theme;
    private String author;
    private long answers;
    private long viewers;
    private Date date;

    public Post() {
    }

    public Post(long id, String theme, String author, long answers, long viewers, Date date) {
        this.id = id;
        this.theme = theme;
        this.author = author;
        this.answers = answers;
        this.viewers = viewers;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getAnswers() {
        return answers;
    }

    public void setAnswers(long answers) {
        this.answers = answers;
    }

    public long getViewers() {
        return viewers;
    }

    public void setViewers(long viewers) {
        this.viewers = viewers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                answers == post.answers &&
                viewers == post.viewers &&
                Objects.equals(theme, post.theme) &&
                Objects.equals(author, post.author) &&
                Objects.equals(date, post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, author, answers, viewers, date);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", author='" + author + '\'' +
                ", answers=" + answers +
                ", viewers=" + viewers +
                ", date=" + date +
                '}';
    }
}
