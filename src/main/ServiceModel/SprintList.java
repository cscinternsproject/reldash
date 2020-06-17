package main.ServiceModel;

import main.ServiceModel.Issues.ApiObject.sprint;

import java.util.List;

public class SprintList {
    private  int startAt;
    private  int maxResults;
    private  int total;
    private List<sprint> values;

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

    public List<sprint> getValues() {
        return values;
    }

    public void setValues(List<sprint> values) {
        this.values = values;
    }
}
