package com.example.myexplist;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context Ctx;
    private static DatabaseClient instance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context ctx){
        this.Ctx = ctx;

        appDatabase = Room.databaseBuilder(Ctx, AppDatabase.class, "MyExpList").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    public static synchronized DatabaseClient getInstance(Context ctx){
        if (instance == null){
            instance = new DatabaseClient(ctx);
        }
        return  instance;
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }

}
