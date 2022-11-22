package com.example.myexplist.anime_resources;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myexplist.R;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private Context ctx;
    private List<Anime> animeList;

    public AnimeAdapter(Context ctx, List<Anime> animeList) {
        this.ctx = ctx;
        this.animeList = animeList;
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.anime_element, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder holder, int position) {
        Anime a = animeList.get(position);
        holder.textViewTitle.setText(a.getTitle());
        holder.textViewIsViewed.setText(a.getIsViewed());
        holder.textViewEpisodes.setText(Integer.toString(a.getEpisodes()));
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewIsViewed, textViewEpisodes;

        public AnimeViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewAnimeTitle);
            textViewIsViewed = itemView.findViewById(R.id.textViewIsViewed);
            textViewEpisodes = itemView.findViewById(R.id.textViewEpisodes);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Anime anime = animeList.get(getAdapterPosition());

            Intent intent = new Intent(ctx, UpdateAnimeActivity.class);
            intent.putExtra("anime", anime);

            ctx.startActivity(intent);
        }
    }

}
