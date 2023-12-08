package com.shivshakti.notemate.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.shivshakti.notemate.Model.NotesEntity;
import com.shivshakti.notemate.Repository.NotesRepository;
import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<NotesEntity>> getAllNotes;

    public LiveData<List<NotesEntity>> highToLow;
    public LiveData<List<NotesEntity>> lowToHigh;

    public NotesViewModel(Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNote;

        highToLow = repository.highToLow;
        lowToHigh = repository.lowToHigh;
    }

    public void insertNote(NotesEntity notes)
    {
        repository.insertNotes(notes);
    }

    public void deleteNote(int id)
    {
        repository.deleteNotes(id);
    }

    public void updateNote(NotesEntity notes)
    {
        repository.updateNotes(notes);
    }

}
