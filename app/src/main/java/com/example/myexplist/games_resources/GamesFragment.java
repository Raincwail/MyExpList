package com.example.myexplist.games_resources;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myexplist.DatabaseClient;
import com.example.myexplist.MainActivityInterface;
import com.example.myexplist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class GamesFragment extends Fragment {

    private FloatingActionButton buttonAddGames;
    private RecyclerView recyclerView;
    private MainActivityInterface listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivityInterface) {
            listener = (MainActivityInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MainActivityInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_games, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.games_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonAddGames = getView().findViewById(R.id.addGamesButton);
        buttonAddGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddGamesActivity.class);
                startActivity(intent);
            }
        });

        if (listener.getFilter().equals("All")) {
            getGames();
        } else {
            getFilteredGames(listener.getFilter());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listener.getFilter().equals("All")) {
            getGames();
        } else {
            getFilteredGames(listener.getFilter());
        }
    }

    private void getGames() {
        class GetGames extends AsyncTask<Void, Void, List<Games>> {
            @Override
            protected List<Games> doInBackground(Void... voids){
                List<Games> gamesList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .gamesDao()
                        .getAll();
                return gamesList;
            }

            @Override
            protected void onPostExecute(List<Games> games) {
                super.onPostExecute(games);
                GamesAdapter adapter = new GamesAdapter(getContext(), games);
                recyclerView.setAdapter(adapter);
            }
        }

        GetGames gg = new GetGames();
        gg.execute();
    }

    private void getFilteredGames(String status) {
        class GetGames extends AsyncTask<Void, Void, List<Games>> {
            @Override
            protected List<Games> doInBackground(Void... voids){
                List<Games> gamesList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .gamesDao()
                        .getByStatus(status);
                return gamesList;
            }

            @Override
            protected void onPostExecute(List<Games> games) {
                super.onPostExecute(games);
                GamesAdapter adapter = new GamesAdapter(getContext(), games);
                recyclerView.setAdapter(adapter);
            }
        }

        GetGames gg = new GetGames();
        gg.execute();
    }

}
