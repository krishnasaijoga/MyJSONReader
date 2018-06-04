package com.example.myjsonreader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by developer on 5/25/18.
 */

class GitUserExtraDetails {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;

    @SerializedName("html_url")
    @Expose
    private String html_url;

    @SerializedName("gists_url")
    @Expose
    private String gists_url;

    @SerializedName("repos_url")
    @Expose
    private String repos_url;

    @SerializedName("events_url")
    @Expose
    private String events_url;


    public String getHtml_url() {
        return html_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }
}
