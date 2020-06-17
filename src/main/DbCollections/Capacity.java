package main.DbCollections;

import java.util.Date;

public class Capacity {
    private String project;
    private String key;
    private String  Assignee;
    private String sprint;
    private String release;
    private Double MemberCapacity=25.0;
    private Date SstartDate;
    private Date SendDate;
    private Date RstartDate;
    private Date RendDate;


    public Capacity(String project, String key, String assignee, String sprint, String release) {
        this.project = project;
        this.key = key;
        Assignee = assignee;
        this.sprint = sprint;
        this.release = release;

    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAssignee() {
        return Assignee;
    }

    public void setAssignee(String assignee) {
        Assignee = assignee;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Double getMemberCapacity() {
        return MemberCapacity;
    }

    public void setMemberCapacity(Double memberCapacity) {
        MemberCapacity = memberCapacity;
    }

    public Date getSstartDate() {
        return SstartDate;
    }

    public void setSstartDate(Date sstartDate) {
        SstartDate = sstartDate;
    }

    public Date getSendDate() {
        return SendDate;
    }

    public void setSendDate(Date sendDate) {
        SendDate = sendDate;
    }

    public Date getRstartDate() {
        return RstartDate;
    }

    public void setRstartDate(Date rstartDate) {
        RstartDate = rstartDate;
    }

    public Date getRendDate() {
        return RendDate;
    }

    public void setRendDate(Date rendDate) {
        RendDate = rendDate;
    }
}
