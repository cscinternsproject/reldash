package main.ServiceModel.BoardID;

import java.util.List;

public class BoardList {


    private  int startAt;
    private  int maxResults;
    private  int total;
    private List<board> values;

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

    public List<board> getValues() {
        return values;
    }

    public void setValues(List<board> values) {
        this.values = values;
    }
}
