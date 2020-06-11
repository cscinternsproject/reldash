package main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;

public class ReleaseModel {
    @JsonIgnore
    private long openStatus;
    @JsonIgnore
    private long progStatus;
    @JsonIgnore
    private long closeStatus;
    private Date RstartDate;
    private Date RendDate;
    private String sprint;
    private Date SstartDate;
    private Date SendDate;
      @JsonIgnore
    private double SprintCapacity;
    @JsonIgnore
    private double ExpectedCapacity;
    private String colorLabel;
    private String sprintColor;
    private Double openPerc;
    private String project;
    private String release;

    private Double progPerc;
    private Double closPerc;
    private Double releasePerc;
    private Double total;


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
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
    public String getSprintColor() {
        return sprintColor;
    }

    public void setSprintColor(String sprintColor) {
        this.sprintColor = sprintColor;
    }


    public Double getSprintPerc() {
        return SprintPerc;
    }

    public void setSprintPerc() {
        double diff = SprintCapacity - ExpectedCapacity;
        if(diff<0)
        {
            SprintPerc = 0.0;
            return;
        }
        SprintPerc = diff/ExpectedCapacity;

    }

    private Double SprintPerc;

    public Double getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = (double)(openStatus+progStatus+closeStatus);
    }

    public Double getOpenPerc() {
        return openPerc;
    }



    public void setOpenPerc() {
        this.openPerc = openStatus/total;
    }

    public Double getProgPerc() {
        return progPerc;
    }

    public void setProgPerc() {
        this.progPerc = progStatus/total;
    }

    public Double getClosPerc() {
        return closPerc;
    }

    public void setClosPerc() {
        this.closPerc = closeStatus/total;
    }

    public Double getReleasePerc() {
        return releasePerc;
    }

    public void setReleasePerc() {
        long diff = RendDate.getTime() - RstartDate.getTime();
        long days =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        Date period = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(period);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        period=cal.getTime();
        long curr_diff = RendDate.getTime()-period.getTime();
        double curr_period = (double) TimeUnit.DAYS.convert(curr_diff, TimeUnit.MILLISECONDS);
        System.out.println(days+ " "+curr_period);
        this.releasePerc = curr_period/days;
    }

    public long getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(long openStatus) {
        this.openStatus = openStatus;
    }

    public long getProgStatus() {
        return progStatus;
    }

    public void setProgStatus(long progStatus) {
        this.progStatus = progStatus;
    }

    public long getCloseStatus() {
        return closeStatus;
    }

    public void setCloseStatus(long closeStatus) {
        this.closeStatus = closeStatus;
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

    public Double getSprintCapacity() {
        return SprintCapacity;
    }

    public void setSprintCapacity(Double sprintCapacity) {
        SprintCapacity = sprintCapacity;
    }

    public Double getExpectedCapacity() {
        return ExpectedCapacity;
    }

    public void setExpectedCapacity(Double expectedCapacity) {
        ExpectedCapacity = expectedCapacity;
    }

    public String getColorLabel() {
        return colorLabel;
    }

    public void setColorLabel(String colorLabel) {
        this.colorLabel = colorLabel;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public void setData(long openStatus, long progStatus , long closeStatus, Double sprintCapacity, Double expectedCapacity, Date
                        start, Date end, String sprint, Date sstartDate, Date sendDate)
    {
        this.openStatus=openStatus;
        this.closeStatus=closeStatus;
        this.progStatus=progStatus;
        this.SprintCapacity=sprintCapacity;
        this.ExpectedCapacity=expectedCapacity;
        this.RstartDate=start;
        this.RendDate=end;
        this.sprint =sprint;
        this.SstartDate=sstartDate;
        this.SendDate=sendDate;

        setTotal();
        setOpenPerc();
        setClosPerc();
        setProgPerc();
        setReleasePerc();
        setSprintPerc();
        setSprintColor("Green");
        setColorLabel("Green");


    }



}
