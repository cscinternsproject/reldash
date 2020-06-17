package main.ReqMapModel;

public class request {

    private String project;
    private String version;
    private String team;

    public String getProject() {
        return project;
    }

    public String getVersion() {
        return version;
    }

    public String getTeam() {
        return team;
    }

    public request(String project, String version, String sprint) {
        this.project = project;
        this.version = version;
        this.team = sprint;
    }

}

