package com.example.android.newstoday;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.newstoday.R.id.search;

public class MainActivity extends AppCompatActivity {
    private static final String GUARDIAN_API_URL = "http://content.guardianapis.com/search?q=";
    private static final int NEWS_LOADER_ID = 1;
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
                String searchQuery = ((EditText) findViewById(R.id.editTextBeforeClick)).getText().toString().replace(" ", "%20");

                //Check if user input is empty or it contains some query text
                if (searchQuery.isEmpty()) {
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
                    searchQueriedFor.setText(searchQuery);
                }
            }
        });
    }
}
