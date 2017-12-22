package com.pratik.movies.models;

public class MovieHealthCheck {

    private final long id;
    private final String content;

    public MovieHealthCheck (long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
