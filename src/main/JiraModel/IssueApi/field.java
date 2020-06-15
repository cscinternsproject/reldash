package main.JiraModel.IssueApi;

import main.JiraModel.IssueApi.ApiObject.*;

import java.util.List;

public class field {

    private issuetype issuetype;
    private sprint sprint;
    private project project;
    private List<releaseMod> fixVersions;
    private status status;
    private component component;

    private assignee assignee;

    private String summary;


    public issuetype getIssuetype() {
        if(issuetype!=null)
            return issuetype;
        else {
           issuetype obj= new issuetype();
           obj.setName("Not available");
           return obj;
        }
    }

    public void setIssuetype(issuetype issuetype) {
        this.issuetype = issuetype;
    }

    public sprint getSprint() {
        if(sprint!=null)
        return sprint;
        else {
            sprint obj= new sprint();
            obj.setName("Not available");
            return obj;
        }



    }

    public void setSprint(sprint sprint) {
        this.sprint = sprint;
    }

    public project getProject() {
        return project;
    }

    public void setProject(project project) {
        this.project = project;
    }

    public List<releaseMod> getFixVersions() {
        return fixVersions;
    }

    public void setFixVersions(List<releaseMod> fixVersions) {
        this.fixVersions = fixVersions;
    }

    public main.JiraModel.IssueApi.ApiObject.assignee getAssignee() {
        if(assignee!=null)
            return assignee;
        else {
            assignee obj= new assignee();
            obj.setDisplayName("Not available");
            return obj;
        }
    }

    public void setAssignee(main.JiraModel.IssueApi.ApiObject.assignee assignee) {
        this.assignee = assignee;
    }

    public status getStatus() {
        if(status!=null)
            return status;
        else {
            status obj= new status();
            obj.setName("Not available");
            return obj;
        }
    }

    public void setStatus(status status) {
        this.status = status;
    }

    public component getComponent() {
        if(component!=null)
            return component;
        else {
            component obj= new component();
            obj.setName("Not available");
            return obj;
        }
    }

    public void setComponent(component component) {
        this.component = component;
    }



    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
