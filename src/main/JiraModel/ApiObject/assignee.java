package main.JiraModel.ApiObject;

import org.springframework.beans.factory.annotation.Value;

public class assignee {

    private  String  displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
