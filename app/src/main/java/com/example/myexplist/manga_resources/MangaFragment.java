package com.example.myexplist.manga_resources;

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

public class MangaFragment extends Fragment {

    private FloatingActionButton buttonAddManga;
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
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_manga, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.manga_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonAddManga = getView().findViewById(R.id.addMangaButton);
        buttonAddManga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMangaActivity.class);
                startActivity(intent);
            }
        });

        if (listener.getFilter().equals("All")) {
            getManga();
        } else {
            getFilteredManga(listener.getFilter());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listener.getFilter().equals("All")) {
            getManga();
        } else {
            getFilteredManga(listener.getFilter());
        }
    }

    private void getManga() {
        class GetManga extends AsyncTask<Void, Void, List<Manga>> {
            @Override
            protected List<Manga> doInBackground(Void... voids){
                List<Manga> mangaList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .mangaDao()
                        .getAll();
                return mangaList;
            }

            @Override
            protected void onPostExecute(List<Manga> manga) {
                super.onPostExecute(manga);
                MangaAdapter adapter = new MangaAdapter(getContext(), manga);
                recyclerView.setAdapter(adapter);
            }
        }

        GetManga gm = new GetManga();
        gm.execute();
    }

    private void getFilteredManga(String status) {
        class GetManga extends AsyncTask<Void, Void, List<Manga>> {
            @Override
            protected List<Manga> doInBackground(Void... voids){
                List<Manga> mangaList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .mangaDao()
                        .getByStatus(status);
                return mangaList;
            }

            @Override
            protected void onPostExecute(List<Manga> manga) {
                super.onPostExecute(manga);
                MangaAdapter adapter = new MangaAdapter(getContext(), manga);
                recyclerView.setAdapter(adapter);
            }
        }

        GetManga gm = new GetManga();
        gm.execute();
    }

}
