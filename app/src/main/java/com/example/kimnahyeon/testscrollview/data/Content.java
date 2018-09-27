package com.example.kimnahyeon.testscrollview.data;

public class Content {
    private int tid;
    private String tag; //교통, 식사, 쇼핑, 관람, 기타
    private String title;
    private String detail;
    private float price;
    private String concurrency; //원, 달러, 유로, 코로나, 즈워티, ...

    public Content(){}

    public Content(int tid, String tag, String title, float price, String concurrency){
        this.tid = tid;
        this.tag=tag;
        this.title = title;
        this.price=price;
        this.concurrency=concurrency;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(String concurrency) {
        this.concurrency = concurrency;
    }
}
