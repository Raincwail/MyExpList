package com.example.myexplist.manga_resources;

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
import com.example.myexplist.R;

public class AddMangaActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextChapters;
    private Spinner editSpinnerIsRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_manga);

        editTextTitle = findViewById(R.id.editTextMangaTitle);
        editTextChapters = findViewById(R.id.editTextChaptersNum);
        editSpinnerIsRead = findViewById(R.id.spinnerIsRead);

        findViewById(R.id.saveManga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveManga();
            }
        });
    }

    private void saveManga() {
        final String sTitle = editTextTitle.getText().toString().trim();
        final String sChapters = editTextChapters.getText().toString().trim();
        final Object sIsRead = editSpinnerIsRead.getSelectedItem();

        if (sTitle.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return;
        }

        if (sChapters.isEmpty()) {
            editTextChapters.setError("Chapters required");
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

                Manga manga = new Manga();
                manga.setTitle(sTitle);
                manga.setChapters(Integer.parseInt(sChapters));
                manga.setIsRead((String) sIsRead);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .mangaDao()
                        .insert(manga);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveManga sm = new SaveManga();
        sm.execute();

    }

}

