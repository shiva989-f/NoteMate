package com.shivshakti.notemate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shivshakti.notemate.Model.NotesEntity;
import com.shivshakti.notemate.R;
import com.shivshakti.notemate.ViewModel.NotesViewModel;
import com.shivshakti.notemate.databinding.ActivityInsertNoteBinding;

import java.util.Date;

public class InsertNoteActivity extends AppCompatActivity {

    ActivityInsertNoteBinding  binding;
    NotesViewModel notesViewModel;
    String title, subtitle, note, priority = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);


        binding.doneImg.setOnClickListener(v ->{

            title = binding.titleET.getText().toString();
            subtitle = binding.subtitleET.getText().toString();
            note = binding.noteET.getText().toString();

            if (title.isEmpty() && subtitle.isEmpty() && note.isEmpty()){
                Snackbar emptyNote = Snackbar.make(binding.insertNoteLayout, "All Fields are empty", Snackbar.LENGTH_INDEFINITE).setAction("Close", v1 -> {
                    // Empty because, I don't want any operation here
                }).setActionTextColor(Color.parseColor("#E2463F"))
                        .setTextColor(Color.parseColor("#FFEB3B"));
                emptyNote.show();
            }
            else
                createNotes(title, subtitle, note, priority);

        });

        // Priority click listener
        binding.lowPriorityImg.setOnClickListener(v ->{
            priority = "1";
            binding.lowPriorityImg.setImageResource(R.drawable.ic_check);
            // Clear the image if exists
            binding.mediumPriorityImg.setImageResource(0);
            binding.highPriorityImg.setImageResource(0);
        });

        binding.mediumPriorityImg.setOnClickListener(v ->{
            priority = "2";
            binding.mediumPriorityImg.setImageResource(R.drawable.ic_check);
            // Clear the image if exists
            binding.lowPriorityImg.setImageResource(0);
            binding.highPriorityImg.setImageResource(0);
        });

        binding.highPriorityImg.setOnClickListener(v ->{
            priority = "3";
            binding.highPriorityImg.setImageResource(R.drawable.ic_check);
            // Clear the image if exists
            binding.lowPriorityImg.setImageResource(0);
            binding.mediumPriorityImg.setImageResource(0);
        });

    }


    private void createNotes(String title, String subtitle, String note, String priority) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("dd/MM/yyyy", date.getTime());

        NotesEntity notes = new NotesEntity();
        notes.noteTitle = title;
        notes.subTitle = subtitle;
        notes.noteDate = sequence.toString();
        notes.notesPriority = priority;
        notes.notes = note;

        notesViewModel.insertNote(notes);

        Toast.makeText(this, "Notes created successfully", Toast.LENGTH_SHORT).show();
        finish();

    }
}