package com.example.kimnahyeon.testscrollview.data;

public class Content {
    private String tag;
    private String detail;
    private int price;
    private String concurrency;

    public Content(String tag, String detail, int price, String concurrency){
        this.tag=tag;
        this.detail=detail;
        this.price=price;
        this.concurrency=concurrency;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(String concurrency) {
        this.concurrency = concurrency;
    }
}
