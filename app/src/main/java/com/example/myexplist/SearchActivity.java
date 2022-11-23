package com.example.myexplist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myexplist.anime_resources.Anime;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.searchResultView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editTextSearch = findViewById(R.id.searchBar);

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    getResults();
                }
                return handled;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getResults();
    }

    private void getResults() {

        final String sTitle = editTextSearch.getText().toString().trim();

        class GetAnime extends AsyncTask<Void, Void, List<Anime>> {
            @Override
            protected List<Anime> doInBackground(Void... voids) {
                List<Anime> animeList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .animeDao()
                        .getSearched(sTitle);
                return animeList;
            }

            @Override
            protected void onPostExecute(List<Anime> anime) {
                super.onPostExecute(anime);
                SearchAdapter adapter = new SearchAdapter(getApplicationContext(), anime);
                recyclerView.setAdapter(adapter);
            }
        }

        GetAnime ga = new GetAnime();
        ga.execute();
    }
}
