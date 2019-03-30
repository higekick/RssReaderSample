package com.higekick.rssreadersample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.higekick.rssreadersample.RssReaderManager.RssItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mList;
    RssRecycleViewAdapter adapter = new RssRecycleViewAdapter();
    FloatingActionButton fab;
    EditText editUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setLayout();
    }

    /** set layout */
    private void setLayout() {
        fab = findViewById(R.id.fab);
        editUrl = findViewById(R.id.edit_url);
        editUrl.setText(RssReaderManager.URL_RSS);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start Loading RSS", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                loadRss(editUrl.getText().toString());
            }
        });
        mList = findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mList.setHasFixedSize(true);
        mList.setAdapter(adapter);
        mList.setLayoutManager(llm);
    }

    /** execute loading RSS */
    private void loadRss(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        new AsyncTask<String, Void, List<RssItem>>() {
            @Override
            protected List<RssItem> doInBackground(String ... url) {
                return RssReaderManager.loadRss(url[0]);
            }

            @Override
            protected void onPostExecute(List<RssItem> rssItems) {
                adapter.setList(rssItems);
            }

        }.execute(url);
    }

}
