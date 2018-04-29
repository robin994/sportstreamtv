package com.robin994.streamsoccer.Data;

import com.robin994.streamsoccer.Connection.RssGetter;

import java.util.List;

public class MatchList {

    private static RssGetter rssTask;
    private static final long DAY = 24 * 60 * 60 * 1000;

    public MatchList() {
    }

    public static List<Entry> getRssMatches(String url)  {
        rssTask = new RssGetter();
        rssTask.setUrl(url);
        EntryListBuilder<Entry> entryListBuilder = new EntryListBuilder();
        String xml = rssTask.GetRssData(url);
        //Log.d("XML RSS", xml);
        return entryListBuilder.getEntries(xml);
    }



}

