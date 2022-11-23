package com.example.myexplist.manga_resources;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myexplist.R;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

    private Context ctx;
    private List<Manga> mangaList;

    public MangaAdapter(Context ctx, List<Manga> mangaList){
        this.ctx = ctx;
        this.mangaList = mangaList;
    }

    @Override
    public MangaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(ctx).inflate(R.layout.manga_element, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MangaViewHolder holder, int position){
        Manga m = mangaList.get(position);
        holder.textViewTitle.setText(m.getTitle());
        holder.textViewIsRead.setText(m.getIsRead());
        holder.textViewChapters.setText(Integer.toString(m.getChapters()));
    }

    @Override
    public int getItemCount() { return mangaList.size(); }

    class MangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewIsRead, textViewChapters;

        public MangaViewHolder(View itemView){
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewMangaTitle);
            textViewIsRead = itemView.findViewById(R.id.textViewIsRead);
            textViewChapters = itemView.findViewById(R.id.textViewChapters);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Manga manga = mangaList.get(getAdapterPosition());

            Intent intent = new Intent(ctx, UpdateMangaActivity.class);
            intent.putExtra("manga", manga);

            ctx.startActivity(intent);
        }

    }


}
