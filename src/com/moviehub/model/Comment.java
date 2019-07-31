package com.moviehub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id_comment")
    @Expose
    private String id_comment;
    @SerializedName("id_film")
    @Expose
    private  String id_film;
    @SerializedName("username_")
    @Expose
    private String username_;
    @SerializedName("content")
    @Expose
    private String content;

    public Comment() {
    }

    public Comment(String id_comment, String id_film, String username_, String content) {
        this.id_comment = id_comment;
        this.id_film = id_film;
        this.username_ = username_;
        this.content = content;
    }

    public String getFilmId() {
        return id_film;
    }

    public void setFilmId(String filmId) {
        this.id_film = filmId;
    }

    public String getUsername() {
        return username_;
    }

    public void setUsername(String username) {
        this.username_ = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentId() {
        return id_comment;
    }

    public void setCommentId(String commentId) {
        this.id_comment = commentId;
    }
}
