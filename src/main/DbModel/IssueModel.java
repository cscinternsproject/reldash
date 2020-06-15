package main.DbModel;

public class IssueModel {
    private String key;
    private String id;
    private String IssueType;
    private String Summary;
    private String status;
    private String  Assignee;
    private String component;
    private String sprint;

    public IssueModel(String key, String id, String issueType, String summary,
                      String status, String assignee, String component, String sprint) {
        this.key = key;
        this.id = id;
        IssueType = issueType;
        Summary = summary;
        this.status = status;
        Assignee = assignee;
        this.component = component;
        this.sprint = sprint;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueType() {
        return IssueType;
    }

    public void setIssueType(String issueType) {
        IssueType = issueType;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return Assignee;
    }

    public void setAssignee(String assignee) {
        Assignee = assignee;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }
}
