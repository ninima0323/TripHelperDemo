package com.example.kimnahyeon.testscrollview.data;

public class Statistics {
    private int totalMonth;
    private int totalDay;
    private String t1, t2;

    public Statistics(String t1, String t2){
        this.t1=t1;
        this.t2=t2;
    }

    public int getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(int totalMonth) {
        this.totalMonth = totalMonth;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }
}
