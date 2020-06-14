package main.JiraModel.ProjectApi;

import main.JiraModel.ApiObject.sprint;

import java.util.List;

public class project {

    private String key;
    private  String name;
    private String self;
    private List<version> releases;
    private List<sprint> sprints;

    public List<version> getReleases() {
        return releases;
    }

    public void setReleases(List<version> releases) {
        this.releases = releases;
    }

    public List<sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<sprint> sprints) {
        this.sprints = sprints;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
