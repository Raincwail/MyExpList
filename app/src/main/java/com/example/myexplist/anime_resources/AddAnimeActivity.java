package com.example.myexplist.anime_resources;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myexplist.DatabaseClient;
import com.example.myexplist.MainActivity;
import com.example.myexplist.R;

public class AddAnimeActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextEpisodes;
    private Spinner editSpinnerIsViewed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_anime);

        editTextTitle = findViewById(R.id.editTextAnimeTitle);
        editTextEpisodes = findViewById(R.id.editTextEpisodesNum);
        editSpinnerIsViewed = findViewById(R.id.spinnerIsViewed);

        findViewById(R.id.saveAnime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAnime();
            }
        });
    }

    private void saveAnime() {
        final String sTitle = editTextTitle.getText().toString().trim();
        final String sEpisodes = editTextEpisodes.getText().toString().trim();
        final Object sIsViewed = editSpinnerIsViewed.getSelectedItem();

        if (sTitle.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return;
        }

        if (sEpisodes.isEmpty()) {
            editTextEpisodes.setError("Episodes required");
            editTextEpisodes.requestFocus();
            return;
        }

        if (editSpinnerIsViewed.getSelectedItem() == null) {
            TextView errorText = (TextView) editSpinnerIsViewed.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Choice required");
            editSpinnerIsViewed.requestFocus();
            return;
        }

        class SaveAnime extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Anime anime = new Anime();
                anime.setTitle(sTitle);
                anime.setEpisodes(Integer.parseInt(sEpisodes));
                anime.setIsViewed((String) sIsViewed);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .animeDao()
                        .insert(anime);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveAnime sa = new SaveAnime();
        sa.execute();

    }
}
