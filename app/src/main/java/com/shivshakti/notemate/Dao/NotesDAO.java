package com.shivshakti.notemate.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shivshakti.notemate.Model.NotesEntity;

import java.util.List;

@Dao
public interface NotesDAO {

    // Getting all notes in getAllNotes function
    @Query("Select * From NotesDB")
    // List<NotesEntity> getAllNotes();
    LiveData<List<NotesEntity>> getAllNotes();

    @Query("Select * From NotesDB ORDER BY notes_priority DESC")
    LiveData<List<NotesEntity>> highToLow();

    @Query("Select * From NotesDB ORDER BY notes_priority ASC")
    LiveData<List<NotesEntity>> lowToHigh();


    // Inserting Note
    @Insert
    void insertNote(NotesEntity... note);


    // Deleting note
    @Query("Delete from NotesDB Where id=:id")
    void deleteNote(int id);


    // Updating note
    @Update
    void updateNote(NotesEntity notes);

}
