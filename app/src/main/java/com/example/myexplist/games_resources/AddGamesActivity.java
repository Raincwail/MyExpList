package com.example.myexplist.games_resources;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myexplist.DatabaseClient;
import com.example.myexplist.R;

public class AddGamesActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private Spinner editSpinnerIsFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_games);

        editTextTitle = findViewById(R.id.editTextGameTitle);
        editSpinnerIsFinished = findViewById(R.id.spinnerIsFinished);

        findViewById(R.id.saveGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGame();
            }
        });
    }

    private void saveGame() {
        final String sTitle = editTextTitle.getText().toString().trim();
        final Object sIsFinished = editSpinnerIsFinished.getSelectedItem();

        if (sTitle.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return;
        }

        class SaveGame extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Games games = new Games();
                games.setTitle(sTitle);
                games.setIsFinished((String) sIsFinished);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .gamesDao()
                        .insert(games);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveGame sg = new SaveGame();
        sg.execute();

    }
}
