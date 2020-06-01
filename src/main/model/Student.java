package main.model;

public class Student {

    private String name;
    private String eligible;
    private int attendance;
    private int  score;
    private String result;


    public  Student(String name, String eligible, int attendance, int score, String result) {
        this.name=name;
        this.eligible=eligible;
        this.attendance=attendance;
        this.score=score;
        this.result=result;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEligible() {
        return eligible;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}