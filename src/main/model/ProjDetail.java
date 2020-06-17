package main.model;

import main.DbCollections.ProjList;

import java.util.List;

public class ProjDetail {

    private int total;
    private int start;
    private List<ProjList> values;

    public List<ProjList> getValues() {
        return values;
    }

    public void setValues(List<ProjList> values) {
        this.values = values;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }


}
