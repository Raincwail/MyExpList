package com.example.myexplist.anime_resources;

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

public class AnimeFragment extends Fragment {

    private FloatingActionButton buttonAddAnime;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_anime, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView =  getView().findViewById(R.id.anime_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonAddAnime =  getView().findViewById(R.id.addAnimeButton);
        buttonAddAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddAnimeActivity.class);
                startActivity(intent);
            }
        });

        if (listener.getFilter().equals("All")) {
            getAnime();
        } else {
            getFilteredAnime(listener.getFilter());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listener.getFilter().equals("All")) {
            getAnime();
        } else {
            getFilteredAnime(listener.getFilter());
        }
    }

    private void getAnime() {
        class GetAnime extends AsyncTask<Void, Void, List<Anime>> {
            @Override
            protected List<Anime> doInBackground(Void... voids){
                return DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .animeDao()
                        .getAll();
            }

            @Override
            protected void onPostExecute(List<Anime> anime) {
                super.onPostExecute(anime);
                AnimeAdapter adapter = new AnimeAdapter(getContext(), anime);
                recyclerView.setAdapter(adapter);
            }
        }

        GetAnime ga = new GetAnime();
        ga.execute();
    }

    private void getFilteredAnime(String status) {
        class GetAnime extends AsyncTask<Void, Void, List<Anime>> {
            @Override
            protected List<Anime> doInBackground(Void... voids){
                return DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .animeDao()
                        .getByStatus(status);
            }

            @Override
            protected void onPostExecute(List<Anime> anime) {
                super.onPostExecute(anime);
                AnimeAdapter adapter = new AnimeAdapter(getContext(), anime);
                recyclerView.setAdapter(adapter);
            }
        }

        GetAnime ga = new GetAnime();
        ga.execute();
    }

}
