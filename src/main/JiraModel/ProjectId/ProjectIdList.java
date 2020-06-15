package main.JiraModel.ProjectId;

import java.util.List;

public class ProjectIdList {
    private  int startAt;
    private  int maxResults;
    private  int total;
    private List<projectId> values;

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

    public List<projectId> getValues() {
        return values;
    }

    public void setValues(List<projectId> values) {
        this.values = values;
    }
}
