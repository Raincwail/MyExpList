package com.example.myexplist.anime_resources;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class UpdateAnimeActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextEpisodes;
    private Spinner editSpinnerIsViewed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_anime);

        editTextTitle = findViewById(R.id.updTextAnimeTitle);
        editTextEpisodes = findViewById(R.id.updTextEpisodesNum);
        editSpinnerIsViewed = findViewById(R.id.updSpinnerIsViewed);

        final Anime anime = (Anime) getIntent().getSerializableExtra("anime");

        loadAnime(anime);

        findViewById(R.id.updSaveAnime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnime(anime);
            }
        });

        findViewById(R.id.deleteAnime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAnimeActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAnime(anime);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadAnime(Anime anime) {

        editTextTitle.setText(anime.getTitle());
        editTextEpisodes.setText(Integer.toString(anime.getEpisodes()));

        String tmpIsViewed = anime.getIsViewed();

        if (tmpIsViewed.equals("Viewed")) {
            editSpinnerIsViewed.setSelection(0);
        } else if (tmpIsViewed.equals("In process")) {
            editSpinnerIsViewed.setSelection(1);
        } else {
            editSpinnerIsViewed.setSelection(2);
        }

    }

    private void updateAnime(final Anime anime) {
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

        class UpdateAnime extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                anime.setTitle(sTitle);
                anime.setEpisodes(Integer.parseInt(sEpisodes));
                anime.setIsViewed((String) sIsViewed);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .animeDao()
                        .update(anime);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        }

        UpdateAnime ua = new UpdateAnime();
        ua.execute();

    }


    private void deleteAnime(final Anime anime) {
        class DeleteAnime extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .animeDao()
                        .delete(anime);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        DeleteAnime da = new DeleteAnime();
        da.execute();

    }

}
