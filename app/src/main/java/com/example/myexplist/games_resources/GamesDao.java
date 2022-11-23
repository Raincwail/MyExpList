package com.example.myexplist.games_resources;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GamesDao {

    @Query("SELECT * FROM games")
    List<Games> getAll();

    @Insert
    void insert(Games game);

    @Delete
    void delete(Games game);

    @Update
    void update(Games game);

    @Query("SELECT * FROM games WHERE id = :id")
    Games getById(Integer id);

    @Query("SELECT * FROM games WHERE isFinished LIKE :status")
    List<Games> getByStatus(String status);

}
