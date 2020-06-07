package main.model;

public class request {

    private String project;
    private String version;
    private String sprint;

    public String getProject() {
        return project;
    }

    public String getVersion() {
        return version;
    }

    public String getSprint() {
        return sprint;
    }

    public request(String project, String version, String sprint) {
        this.project = project;
        this.version = version;
        this.sprint = sprint;
    }

}

