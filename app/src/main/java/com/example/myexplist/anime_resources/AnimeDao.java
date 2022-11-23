package com.example.myexplist.anime_resources;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnimeDao {

    @Query("SELECT * FROM anime")
    List<Anime> getAll();

    @Insert
    void insert(Anime anime);

    @Delete
    void delete(Anime anime);

    @Update
    void update(Anime anime);

    @Query("SELECT * FROM anime WHERE id = :id")
    Anime getById(Integer id);

    @Query("SELECT * FROM anime WHERE isViewed LIKE :status")
    List<Anime> getByStatus(String status);

//    @Query("SELECT isViewed, 'anime' FROM anime WHERE title LIKE :name" +
//            " UNION ALL " +
//            "SELECT isRead, 'manga' FROM manga WHERE title LIKE :name" +
//            " UNION ALL " +
//            "SELECT isFinished, 'game' FROM games WHERE title LIKE :name")
//    public abstract LiveData<List<searchResult>> getStatus(String name);

    @Query("SELECT id, title, isViewed, episodes FROM anime WHERE title LIKE :name" +
            " UNION ALL " +
            "SELECT id, title, isRead, -1 FROM manga WHERE title LIKE :name" +
            " UNION ALL " +
            "SELECT id, title, isFinished, -2 FROM games WHERE title LIKE :name")
    List<Anime> getSearched(String name);

//    @Query("SELECT isViewed FROM anime WHERE title LIKE :name" +
//            " UNION ALL " +
//            "SELECT isRead FROM manga WHERE title LIKE :name" +
//            " UNION ALL " +
//            "SELECT isFinished FROM games WHERE title LIKE :name")
//    List<String> getStatus(String name);
//
//    @Query("SELECT 'anime' FROM anime WHERE title LIKE :name" +
//            " UNION ALL " +
//            "SELECT 'manga' FROM manga WHERE title LIKE :name" +
//            " UNION ALL " +
//            "SELECT 'game' FROM games WHERE title LIKE :name")
//    List<String> getType(String name);
}
