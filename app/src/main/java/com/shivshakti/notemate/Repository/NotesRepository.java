package com.shivshakti.notemate.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.shivshakti.notemate.Dao.NotesDAO;
import com.shivshakti.notemate.Database.NotesDatabase;
import com.shivshakti.notemate.Model.NotesEntity;

import java.util.List;

public class NotesRepository {

    public NotesDAO notesDAO;
    public LiveData<List<NotesEntity>> getAllNote;
    public LiveData<List<NotesEntity>> highToLow;
    public LiveData<List<NotesEntity>> lowToHigh;

    public NotesRepository (Application application){
        NotesDatabase notesDatabase = NotesDatabase.getDatabaseInstance(application);
        notesDAO = notesDatabase.notesDAO();
        getAllNote = notesDAO.getAllNotes();

        highToLow = notesDAO.highToLow();
        lowToHigh = notesDAO.lowToHigh();
    }

    public void insertNotes(NotesEntity notes)
    {
        notesDAO.insertNote(notes);
    }

    public void deleteNotes(int id)
    {
        notesDAO.deleteNote(id);
    }

    public void updateNotes(NotesEntity note)
    {
        notesDAO.updateNote(note);
    }

}
