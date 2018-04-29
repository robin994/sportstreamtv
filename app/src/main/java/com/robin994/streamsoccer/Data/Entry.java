package com.robin994.streamsoccer.Data;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {
    private String author;
    private String authorUrl;
    private String category;
    private String content;
    private String id;
    private String matchUrl;
    private String dateStr;
    private Date date;
    private String title;
    private String textContent;

    public Entry() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String cathegory) {
        this.category = cathegory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return matchUrl;
    }

    public void setUrl(String matchUrl) {
        this.matchUrl = matchUrl;
    }

    public String getDateStr() {
        return dateStr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Match{" +
                "author='" + author + '\'' +
                ", authorUrl='" + authorUrl + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", matchUrl='" + matchUrl + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", date=" + date +
                ", title='" + title + '\'' +
                '}';
    }

    // EX time string YYYY-MM-DDTHH:MM:SS+00:00
    public void setDateStr(String dateStr) {
        //Log.d("dateStr", dateStr);
        this.dateStr = dateStr;
        this.date = new Date();
        String[] array = dateStr.split("-");
        this.date.setYear(Integer.parseInt(array[0]) - 1900);
        this.date.setMonth(Integer.parseInt(array[1]) - 1);
        String[] temp = array[2].split("T");
        this.date.setDate(Integer.parseInt(temp[0]));
        temp = temp[1].split(":");
        date.setHours(Integer.parseInt(temp[0]));
        date.setMinutes(Integer.parseInt(temp[1]));
        temp = temp[2].split("\\+");
        date.setSeconds(Integer.parseInt(temp[0]));

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getMatchUrl() {
        return matchUrl;
    }

    public String getTextContent() {
        return textContent;
    }
}
