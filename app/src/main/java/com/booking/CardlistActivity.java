package com.booking;

import com.booking.network.LoadJSONTask;
import com.booking.model.Cardlist;
import com.booking.model.Response;
import com.booking.network.NetworkUtil;
import com.booking.utils.Constants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CardlistActivity extends AppCompatActivity implements LoadJSONTask.Listener, AdapterView.OnItemClickListener {


    private ListView mListView;

    public static final String URL = "http://visittampere.fi:80/api/search?type=event";

    private List<HashMap<String, String>> mCardMapList = new ArrayList<>();

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static final String KEY_START = "start";
    private static final String KEY_image = "listImage";



    ProfileActivity profile = new ProfileActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlists);

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(URL);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            finish();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onLoaded(List<Cardlist> cardlists) {

        for (Cardlist card : cardlists) {
            HashMap<String, String> map = new HashMap<>();
            map.put(KEY_TITLE, card.getTitle());
            map.put(KEY_DESC, card.getDescription());
            map.put(KEY_START, card.getStart());
            map.put(KEY_image, card.getImageURL());

            mCardMapList.add(map);
        }

        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, mCardMapList.get(i).get(KEY_TITLE),Toast.LENGTH_LONG).show();
    }

    private void loadListView() {

        ListAdapter adapter = new SimpleAdapter(CardlistActivity.this, mCardMapList, R.layout.cardlist,
                new String[] { KEY_TITLE, KEY_DESC, KEY_START },
                new int[] { R.id.title,R.id.description, R.id.start });

        mListView.setAdapter(adapter);

    }
}