package main.JiraModel.ProjectApi;
import main.JiraModel.Issue;
import java.util.List;

public class ProjectList {

    private  int startAt;
    private  int maxResults;
    private  int total;
    private List<project> values;

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

    public List<project> getValues() {
        return values;
    }

    public void setValues(List<project> values) {
        this.values = values;
    }
}
