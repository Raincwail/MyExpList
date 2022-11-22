package com.example.myexplist.manga_resources;

import android.app.AlertDialog;
import android.app.Fragment;
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
import androidx.fragment.app.FragmentTransaction;

import com.example.myexplist.DatabaseClient;
import com.example.myexplist.MainActivity;
import com.example.myexplist.R;
import com.example.myexplist.anime_resources.UpdateAnimeActivity;

public class UpdateMangaActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextChapters;
    private Spinner editSpinnerIsRead;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_manga);

        editTextTitle = findViewById(R.id.updTextMangaTitle);
        editTextChapters = findViewById(R.id.updTextChaptersNum);
        editSpinnerIsRead = findViewById(R.id.updSpinnerIsRead);

        final Manga manga = (Manga) getIntent().getSerializableExtra("manga");

        loadManga(manga);

        findViewById(R.id.updSaveManga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateManga(manga);
            }
        });

        findViewById(R.id.deleteManga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateMangaActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteManga(manga);
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

    private void loadManga(Manga manga) {

        editTextTitle.setText(manga.getTitle());
        editTextChapters.setText(Integer.toString(manga.getChapters()));

        String tmpIsViewed = manga.getIsRead();

        if (tmpIsViewed.equals("Read")) {
            editSpinnerIsRead.setSelection(0);
        } else if (tmpIsViewed.equals("In process")) {
            editSpinnerIsRead.setSelection(1);
        } else {
            editSpinnerIsRead.setSelection(2);
        }

    }

    private void updateManga(final Manga manga) {
        final String sTitle = editTextTitle.getText().toString().trim();
        final String sChapters = editTextChapters.getText().toString().trim();
        final Object sIsRead = editSpinnerIsRead.getSelectedItem();

        if (sTitle.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return;
        }

        if (sChapters.isEmpty()) {
            editTextChapters.setError("Episodes required");
            editTextChapters.requestFocus();
            return;
        }

        if (editSpinnerIsRead.getSelectedItem() == null) {
            TextView errorText = (TextView) editSpinnerIsRead.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Choice required");
            editSpinnerIsRead.requestFocus();
            return;
        }

        class SaveManga extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                manga.setTitle(sTitle);
                manga.setChapters(Integer.parseInt(sChapters));
                manga.setIsRead((String) sIsRead);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .mangaDao()
                        .update(manga);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        }

        SaveManga sm = new SaveManga();
        sm.execute();

    }

    private void deleteManga(final Manga manga) {
        class DeleteManga extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .mangaDao()
                        .delete(manga);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
            }
        }

        DeleteManga dm = new DeleteManga();
        dm.execute();

    }


}
