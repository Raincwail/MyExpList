package com.example.myexplist.manga_resources;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MangaDao {

    @Query("SELECT * FROM manga")
    List<Manga> getAll();

    @Insert
    void insert(Manga manga);

    @Delete
    void delete(Manga manga);

    @Update
    void update(Manga manga);

    @Query("SELECT * FROM manga WHERE id = :id")
    Manga getById(Integer id);

    @Query("SELECT * FROM manga WHERE isRead LIKE :status")
    List<Manga> getByStatus(String status);

}
