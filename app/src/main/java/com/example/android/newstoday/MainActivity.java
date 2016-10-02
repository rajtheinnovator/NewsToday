package com.example.android.newstoday;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


import static com.example.android.newstoday.NewsLoader.LOG_TAG;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    private static final String GUARDIAN_API_URL = "http://content.guardianapis.com/search?";
    private static final int NEWS_LOADER_ID = 1;
    String searchQuery;
    // display error
    //  TextView mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.searchBoxAfterClick);
        linearLayout.setVisibility(View.GONE);
        Button searchButtonBeforeFirstSearch = (Button) findViewById(R.id.buttonBeforeSearch);
        //Set click Listener on Search Button Click
        searchButtonBeforeFirstSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Find the edit text's actual text and make it compatible for a url search query
                String searchQueried = ((EditText) findViewById(R.id.editTextBeforeClick)).getText().toString();

                //Check if user input is empty or it contains some query text
                if (searchQueried.isEmpty()) {
                    Context context = getApplicationContext();
                    String text = "Nothing Entered in Search";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    LinearLayout searchBoxBeforeClick = (LinearLayout) findViewById(R.id.searchBoxBeforeClick);
                    searchBoxBeforeClick.setVisibility(View.GONE);
                    LinearLayout searchBoxAfterClick = (LinearLayout) findViewById(R.id.searchBoxAfterClick);
                    searchBoxAfterClick.setVisibility(View.VISIBLE);
                    TextView searchQueriedFor = (TextView) findViewById(R.id.searchQueriedFor);
                    searchQueriedFor.setText(searchQueried);
                    searchQuery = searchQueried.replace(" ", "%20");
                    //First of all check if network is connected or not then only start the laoder
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
            /*
            fetch data
            Get a reference to the LoaderManager, in order to interact with loaders.
            And, Initialize the loader. Pass in the int ID constant defined above and pass in null for
            the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            because this activity implements the LoaderCallbacks interface).
        */
                        myLoaderManager();
                    } else {
                        // display error
                    }
                }
            }
        });
    }

    private void myLoaderManager() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(GUARDIAN_API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", searchQuery);
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("api-key", "0a397f99-4b95-416f-9c51-34c711f0069a");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        Log.v(LOG_TAG, "Value of Uri is :" + uriBuilder);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, final ArrayList<News> newses) {

        // Create a new {@link ArrayAdapter} of earthquakes
        NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, newses);

        // Find a reference to the {@link ListView} in the layout
        ListView news_list_view = (ListView) findViewById(R.id.news_list_view);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        news_list_view.setAdapter(newsAdapter);

        news_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News news = newses.get(position);
                Intent goToUrl = new Intent(Intent.ACTION_VIEW);
                goToUrl.setData(Uri.parse(news.getWebUrl()));
                startActivity(goToUrl);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {

    }
}
