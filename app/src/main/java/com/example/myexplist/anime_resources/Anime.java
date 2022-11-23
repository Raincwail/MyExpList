package com.example.myexplist.anime_resources;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Anime implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "episodes")
    private int episodes;

    @ColumnInfo(name = "isViewed")
    private String isViewed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episode) {
        this.episodes = episode;
    }

    public String getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(String isViewed) {
        this.isViewed = isViewed;
    }

}
