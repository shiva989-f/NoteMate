package com.shivshakti.notemate.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shivshakti.notemate.Dao.NotesDAO;
import com.shivshakti.notemate.Model.NotesEntity;

@Database(entities = {NotesEntity.class},version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDAO notesDAO();

    // Variable of notes database instance
    public static NotesDatabase INSTANCE;

    // Method to check if instance is created or not if not then create an instance
    public static NotesDatabase getDatabaseInstance(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NotesDatabase.class, "NotesDB").allowMainThreadQueries().build();
        }
        return INSTANCE;

    }

}
