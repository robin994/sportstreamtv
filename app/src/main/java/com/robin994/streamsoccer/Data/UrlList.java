package com.robin994.streamsoccer.Data;

import android.util.Log;

import com.robin994.streamsoccer.Connection.RssGetter;

import java.util.ArrayList;
import java.util.List;

public class UrlList {


    private static RssGetter rssTask;

    public UrlList() {
    }

    public static List getUrlsList(Entry match) {
        List<Url> toReturn = new ArrayList<>();
        rssTask = new RssGetter();
        rssTask.setUrl(match.getUrl());
        EntryListBuilder<Entry> entryListBuilder = new EntryListBuilder();
        String xml = rssTask.GetRssData(rssTask.getUrl());
        //Log.d("XML RSS", xml);
        List<Entry> list = entryListBuilder.getEntries(xml);
        for (Entry entry : list) {
            toReturn.add(new Url(entry));
        }
        return toReturn;
    }


}
