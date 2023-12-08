package com.shivshakti.notemate.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shivshakti.notemate.Model.NotesEntity;
import com.shivshakti.notemate.R;
import com.shivshakti.notemate.ViewModel.NotesViewModel;
import com.shivshakti.notemate.databinding.ActivityUpdateNoteBinding;

import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    NotesViewModel notesViewModel;
    String upTitle, upSubtitle, upNote, upPriority = "1";
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        // Getting Data from intent and assigning with string
        Intent iGetFromAdapter = getIntent();
        ID = iGetFromAdapter.getIntExtra("NOTE_ID", 0);
        upTitle = iGetFromAdapter.getStringExtra("NOTE_TITLE");
        upSubtitle = iGetFromAdapter.getStringExtra("NOTE_SUBTITLE");
        upPriority = iGetFromAdapter.getStringExtra("NOTE_PRIORITY");
        upNote = iGetFromAdapter.getStringExtra("NOTE");

        // Assigning string in views
        binding.upTitleET.setText(upTitle);
        binding.upSubtitleET.setText(upSubtitle);
        switch (upPriority){
            case "1":
                binding.upLowPriorityImg.setImageResource(R.drawable.ic_check);
                break;
            case "2":
                binding.upMediumPriorityImg.setImageResource(R.drawable.ic_check);
                break;
            case "3":
                binding.upHighPriorityImg.setImageResource(R.drawable.ic_check);
                break;
        }
        binding.upNoteET.setText(upNote);


        binding.upDoneImg.setOnClickListener(v ->{

            upTitle = binding.upTitleET.getText().toString();
            upSubtitle = binding.upSubtitleET.getText().toString();
            upNote = binding.upNoteET.getText().toString();

            if (upTitle.isEmpty() && upSubtitle.isEmpty() && upNote.isEmpty())
                finish();
            else
                updateNote(upTitle, upSubtitle, upNote, upPriority);

        });

        // Priority click listener
        binding.upLowPriorityImg.setOnClickListener(v ->{
            upPriority = "1";
            binding.upLowPriorityImg.setImageResource(R.drawable.ic_check);
            // Clear the image if exists
            binding.upMediumPriorityImg.setImageResource(0);
            binding.upHighPriorityImg.setImageResource(0);
        });

        binding.upMediumPriorityImg.setOnClickListener(v ->{
            upPriority = "2";
            binding.upMediumPriorityImg.setImageResource(R.drawable.ic_check);
            // Clear the image if exists
            binding.upLowPriorityImg.setImageResource(0);
            binding.upHighPriorityImg.setImageResource(0);
        });

        binding.upHighPriorityImg.setOnClickListener(v ->{
            upPriority = "3";
            binding.upHighPriorityImg.setImageResource(R.drawable.ic_check);
            // Clear the image if exists
            binding.upLowPriorityImg.setImageResource(0);
            binding.upMediumPriorityImg.setImageResource(0);
        });

    }

    private void updateNote(String upTitle, String upSubtitle, String upNote, String upPriority) {

        Date upDate = new Date();
        CharSequence formatDate = DateFormat.format("dd/MM/yyyy", upDate.getTime());
        // For month is in letter
        //CharSequence formatDate = DateFormat.format("dd/MM/MM/yyyy", upDate.getTime());

        NotesEntity updateNote = new NotesEntity();
        updateNote.id = ID;
        updateNote.noteTitle = upTitle;
        updateNote.subTitle = upSubtitle;
        updateNote.noteDate = formatDate.toString();
        updateNote.notesPriority = upPriority;
        updateNote.notes = upNote;

        notesViewModel.updateNote(updateNote);

        Toast.makeText(this, "Changes Saved successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_menu){
            BottomSheetDialog delete_sheet = new BottomSheetDialog(UpdateNoteActivity.this, R.style.BottomSheetStyle);
            View view = LayoutInflater.from(UpdateNoteActivity.this)
                    .inflate(R.layout.delete_bottom_sheet, findViewById(R.id.delete_bottom_sheet));
            delete_sheet.setContentView(view);
            delete_sheet.show();

            Button yes_delete_btn, no_delete_btn;

            yes_delete_btn = view.findViewById(R.id.yes_delete_btn);
            no_delete_btn = view.findViewById(R.id.no_delete_btn);

            yes_delete_btn.setOnClickListener(v ->{
                notesViewModel.deleteNote(ID);
                finish();
            });


            no_delete_btn.setOnClickListener(v ->{
                delete_sheet.dismiss();
            });
        }

        return true;
    }
}