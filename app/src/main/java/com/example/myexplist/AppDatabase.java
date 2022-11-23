package com.example.myexplist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myexplist.anime_resources.Anime;
import com.example.myexplist.anime_resources.AnimeDao;
import com.example.myexplist.games_resources.Games;
import com.example.myexplist.games_resources.GamesDao;
import com.example.myexplist.manga_resources.Manga;
import com.example.myexplist.manga_resources.MangaDao;

@Database(entities =  {Anime.class, Manga.class, Games.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AnimeDao animeDao();
    public abstract MangaDao mangaDao();
    public abstract GamesDao gamesDao();
}
