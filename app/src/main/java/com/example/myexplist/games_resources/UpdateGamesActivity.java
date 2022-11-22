package com.example.myexplist.games_resources;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myexplist.DatabaseClient;
import com.example.myexplist.R;

public class UpdateGamesActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private Spinner editSpinnerIsFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_games);

        editTextTitle = findViewById(R.id.updTextGameTitle);
        editSpinnerIsFinished = findViewById(R.id.updSpinnerIsFinished);

        final Games games = (Games) getIntent().getSerializableExtra("games");
        loadGames(games);

        findViewById(R.id.updSaveGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGames(games);
            }
        });

        findViewById(R.id.deleteGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateGamesActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteGames(games);
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

    private void loadGames(Games games) {

        editTextTitle.setText(games.getTitle());

        String tmpIsViewed = games.getIsFinished();

        if (tmpIsViewed.equals("Finished")) {
            editSpinnerIsFinished.setSelection(0);
        } else if (tmpIsViewed.equals("Tried")) {
            editSpinnerIsFinished.setSelection(1);
        } else if (tmpIsViewed.equals("In process")) {
            editSpinnerIsFinished.setSelection(2);
        } else {
            editSpinnerIsFinished.setSelection(3);
        }

    }

    private void updateGames(final Games games) {
        final String sTitle = editTextTitle.getText().toString().trim();
        final Object sIsFinished = editSpinnerIsFinished.getSelectedItem();

        if (sTitle.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return;
        }

        class SaveGames extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                games.setTitle(sTitle);
                games.setIsFinished((String) sIsFinished);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .gamesDao()
                        .update(games);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        }

        SaveGames sg = new SaveGames();
        sg.execute();

    }

    private void deleteGames(final Games games) {
        class DeleteGames extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .gamesDao()
                        .delete(games);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
            }
        }

        DeleteGames dg = new DeleteGames();
        dg.execute();

    }


}
