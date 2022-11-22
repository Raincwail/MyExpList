package com.example.myexplist.games_resources;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myexplist.R;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {

    private Context ctx;
    private List<Games> gamesList;

    public GamesAdapter(Context ctx, List<Games> gamesList){
        this.ctx = ctx;
        this.gamesList = gamesList;
    }

    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(ctx).inflate(R.layout.game_element, parent, false);
        return new GamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GamesViewHolder holder, int position){
        Games g = gamesList.get(position);
        holder.textViewTitle.setText(g.getTitle());
        holder.textViewIsFinished.setText(g.getIsFinished());
    }

    @Override
    public int getItemCount() { return gamesList.size(); }

    class GamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewIsFinished;

        public GamesViewHolder(View itemView){
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewGameTitle);
            textViewIsFinished = itemView.findViewById(R.id.textViewIsFinished);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Games games = gamesList.get(getAdapterPosition());

            Intent intent = new Intent(ctx, UpdateGamesActivity.class);
            intent.putExtra("games", games);

            ctx.startActivity(intent);
        }

    }


}
