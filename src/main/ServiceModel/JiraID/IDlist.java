package main.ServiceModel.JiraID;

import java.util.List;

public class IDlist {
    private  int startAt;
    private  int maxResults;
    private  int total;
    private List<IssueID> issues;

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<IssueID> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueID> issues) {
        this.issues = issues;
    }
}
