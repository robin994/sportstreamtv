package com.robin994.streamsoccer.Data;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import com.robin994.streamsoccer.CardPresenter;
import com.robin994.streamsoccer.Connection.SpinnerFragment;
import com.robin994.streamsoccer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PreparingDataTask extends AsyncTask<Void, Void, ArrayObjectAdapter> {


    private ArrayObjectAdapter toReturn;
    private String[] sources = {
            "https://www.reddit.com/r/soccerstreams/new/.rss",
            "https://www.reddit.com/r/motorsportsstreams/new/.rss"
    };

    private Handler.Callback callback;
    private Context context;
    private Activity activity;
    private SpinnerFragment mSpinnerFragment;

    public PreparingDataTask(Context context, Handler.Callback callback, Activity activity) {
        this.context = context;
        this.callback = callback;
        this.activity = activity;
    }

    @Override
    protected ArrayObjectAdapter doInBackground(Void... voids) {
       return loadRows();
    }

    private ArrayObjectAdapter loadRows() {

        List<Entry> list = new ArrayList<>();
        for (String source : sources)
        {
            List temp = MatchList.getRssMatches(source);
            if (!list.containsAll(temp)) {
                list.addAll(temp);
            }
        }

        Collections.sort(list, new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });


        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();

        int i;
        //Log.d("ARRAY", list.toString());
        for (i = 0; i < list.size(); i++) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            List<Url> listUrl = UrlList.getUrlsList(list.get(i));
            for (int j = 0; j < listUrl.size(); j++) {
                if (listUrl.get(j).hasLink())
                    listRowAdapter.add(listUrl.get(j));
            }
            //Log.d("TITLE", list.get(i).getTitle());
            HeaderItem header = new HeaderItem(i, list.get(i).getTitle());
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }

        /* TODO PREFERENCES

        HeaderItem gridHeader = new HeaderItem(i, "PREFERENCES");

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.grid_view));
        gridRowAdapter.add(getString(R.string.error_fragment));
        gridRowAdapter.add(getResources().getString(R.string.personal_settings));
        rowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));
        */
        toReturn = rowsAdapter;
        return rowsAdapter;
    }

    @Override
    protected void onPostExecute(ArrayObjectAdapter arrayObjectAdapter) {
        super.onPostExecute(arrayObjectAdapter);
        Message msg = new Message();
        msg.obj = toReturn;
        callback.handleMessage(msg);
        activity.getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
    }

    @Override
    protected void onPreExecute() {
        mSpinnerFragment = new SpinnerFragment();
        activity.getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, mSpinnerFragment).commit();
    }
}
