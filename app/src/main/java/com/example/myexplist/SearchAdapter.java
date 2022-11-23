package com.example.myexplist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myexplist.anime_resources.Anime;
import com.example.myexplist.anime_resources.UpdateAnimeActivity;
import com.example.myexplist.games_resources.Games;
import com.example.myexplist.games_resources.UpdateGamesActivity;
import com.example.myexplist.manga_resources.Manga;
import com.example.myexplist.manga_resources.UpdateMangaActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context ctx;
    private List<Anime> searchList;

    public SearchAdapter(Context ctx, List<Anime> searchList){
        this.ctx = ctx;
        this.searchList = searchList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(ctx).inflate(R.layout.search_element, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Anime a = searchList.get(position);

        int type = a.getEpisodes();
        switch (type) {
            case -1:
                holder.textViewTitle.setText(a.getTitle());
                holder.textViewType.setText("Manga");
                holder.textViewProgress.setText(a.getIsViewed());
                break;
            case -2:
                holder.textViewTitle.setText(a.getTitle());
                holder.textViewType.setText("Game");
                holder.textViewProgress.setText(a.getIsViewed());
                break;
            default:
                holder.textViewTitle.setText(a.getTitle());
                holder.textViewType.setText("Anime");
                holder.textViewProgress.setText(a.getIsViewed());
                break;
        }
    }

    @Override
    public int getItemCount() { return searchList.size(); }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTitle, textViewType, textViewProgress;

        public SearchViewHolder(View itemView){
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewSearchTitle);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewProgress = itemView.findViewById(R.id.textViewIsProgress);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Anime anime = searchList.get(getAdapterPosition());
            int type = anime.getEpisodes();
            switch (type) {
                case -1:
                    Manga m = DatabaseClient
                            .getInstance(ctx.getApplicationContext())
                            .getAppDatabase()
                            .mangaDao()
                            .getById(anime.getId());
                    Intent m_intent = new Intent(ctx, UpdateMangaActivity.class);
                    m_intent.putExtra("manga", m);
                    m_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(m_intent);
                    break;
                case -2:
                    Games g = DatabaseClient
                            .getInstance(ctx.getApplicationContext())
                            .getAppDatabase()
                            .gamesDao()
                            .getById(anime.getId());
                    Intent g_intent = new Intent(ctx, UpdateGamesActivity.class);
                    g_intent.putExtra("games", g);
                    g_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(g_intent);
                    break;
                default:
                    Anime a = DatabaseClient
                            .getInstance(ctx.getApplicationContext())
                            .getAppDatabase()
                            .animeDao()
                            .getById(anime.getId());
                    Intent a_intent = new Intent(ctx, UpdateAnimeActivity.class);
                    a_intent.putExtra("anime", a);
                    a_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(a_intent);
                    break;
            }
        }
    }

}

