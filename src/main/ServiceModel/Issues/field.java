package main.ServiceModel.Issues;

import main.ServiceModel.Issues.ApiObject.*;
//import main.ServiceModel.JiraID.IssueApi.ApiObject.*;

import java.util.List;

public class field {

    private main.ServiceModel.Issues.ApiObject.issuetype issuetype;
    private main.ServiceModel.Issues.ApiObject.sprint sprint;
    private main.ServiceModel.Issues.ApiObject.project project;
    private List<releaseMod> fixVersions;
    private main.ServiceModel.Issues.ApiObject.status status;
    private List<component>components;
    private assignee assignee;
    private String summary;
    private int OriginalEstimates=4;
    private int TimeSpent=3;
    private Double MemberCapacity=25.0;

    public Double getMemberCapacity() {
        return MemberCapacity;
    }

    public void setMemberCapacity(Double memberCapacity) {
        MemberCapacity = memberCapacity;
    }

    public int getOriginalEstimates() {

        return OriginalEstimates;
    }

    public void setOriginalEstimates(int originalEstimates) {
        this.OriginalEstimates = originalEstimates;
    }

    public int getTimeSpent() {

        return TimeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.TimeSpent = timeSpent;
    }

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

    public main.ServiceModel.Issues.ApiObject.assignee getAssignee() {
        if(assignee!=null)
            return assignee;
        else {
            assignee obj= new assignee();
            obj.setDisplayName("Not available");
            return obj;
        }
    }

    public void setAssignee(main.ServiceModel.Issues.ApiObject.assignee assignee) {
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

    public List<component> getComponents() {
        if(components.size()==0) {
          component cmp=  new component();
          cmp.setName("not available");
            components.add(cmp);
            return components;
        }
        return  components;
    }

    public void setComponents(List<component> component) {
        this.components = component;
    }



    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
