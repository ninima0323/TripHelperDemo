package com.example.kimnahyeon.testscrollview;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

public class Trip implements Parcelable{
    private String title;
    private String place;
    private int tid;
    private String people = "";
    private final ObservableField<String> firstDate = new ObservableField<>();
    private final ObservableField<String> lastDate = new ObservableField<>();

    public Trip(String title, String place) {
        this.title = title;
        this.place = place;
    }

    protected Trip(Parcel in) {
        title = in.readString();
        place = in.readString();
        tid = in.readInt();
        people = in.readString();
        //firstDate = in.read
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public ObservableField<String> getFirstDate() {
        return firstDate;
    }

    public ObservableField<String> getLastDate() {
        return lastDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(place);
        dest.writeInt(tid);
        dest.writeString(people);
    }
}
