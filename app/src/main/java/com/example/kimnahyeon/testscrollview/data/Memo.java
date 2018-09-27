package com.example.kimnahyeon.testscrollview.data;

public class Memo {
    private int tid;
    private String title;
    private String body;

    public Memo(){}

    public Memo(int tid, String title, String body){
        this.tid = tid;
        this.title=title;
        this.body=body;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
