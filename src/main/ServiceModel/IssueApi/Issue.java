package main.ServiceModel.IssueApi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Issue {
    @JsonProperty(value="id")
    private String JiraID;
    private field fields;

    @JsonIgnore
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public field getFields() {
        return fields;
    }

    public void setFields(field fields) {
        this.fields = fields;
    }

    public String getJiraID() {
        return JiraID;
    }

    public void setJiraID(String id) {
        this.JiraID = id;
    }

}
