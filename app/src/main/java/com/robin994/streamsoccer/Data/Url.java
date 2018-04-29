package com.robin994.streamsoccer.Data;


import android.text.Html;
import android.util.Log;

import com.robin994.streamsoccer.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Url implements Serializable {

    private Entry entry;
    private ArrayList<String> urls;
    private Boolean hasLink;
    private Boolean hasAcestream;
    private Boolean hasGeneralLink;
    private int cardImage;


    public Url(Entry entry) {
        this.entry = entry;
        this.hasLink = false;
        this.hasAcestream = false;
        this.hasAcestream = false;
        this.urls = urlGeneretor(entry.getContent());
        this.getEntry().setTextContent(html2text(this.getEntry().getContent()));
    }

    public Entry getEntry() {
        return entry;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }


    public ArrayList<String> urlGeneretor(String content) {
        //Log.d("CONTENT", content);
        String toAdd;
        ArrayList<String> toReturn = new ArrayList();

        if (content.toLowerCase().contains("href=") || content.toLowerCase().contains("acesteam")) {
            String[] strings = content.split(" ");
            hasLink = true;
            for (String string : strings) {
                if (string.toLowerCase().contains("href=")) {
                    hasGeneralLink = true;
                    cardImage = R.drawable.movie;
                    toAdd = string.replace("href=", "");
                    //Log.d("TOADD REP 1 ", toAdd);
                    toAdd = toAdd.replace("\"", " ");
                    //Log.d("TOADD REP 2 ", toAdd);
                    //Log.d("TOADD SPLIT", toAdd.split(" ")[0]);
                    //Log.d("TOADD SPLIT", toAdd.split(" ")[1]);
                    if (!toReturn.contains(toAdd.split(" ")[1]))
                        toReturn.add(toAdd.split(" ")[1]);
                }
                if (string.toLowerCase().contains("acestream://")) {
                    hasAcestream = true;
                    cardImage = R.drawable.acestream;
                    toAdd = string;
                    if (!toReturn.contains(toAdd))
                        toReturn.add(toAdd.substring(toAdd.toLowerCase().indexOf("acestream://")));
                }
            }
        }
        if (hasAcestream && hasGeneralLink) {
            cardImage = R.drawable.both;
        }

        return toReturn;
    }

    public Boolean hasLink() {
        return hasLink;
    }

    public int getCardImage() {
        return cardImage;
    }

    public static String html2text(String html) {
        return Html.fromHtml(html).toString();
    }
}
