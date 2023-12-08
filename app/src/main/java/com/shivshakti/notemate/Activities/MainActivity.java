package com.shivshakti.notemate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.shivshakti.notemate.Adapter.NotesAdapter;
import com.shivshakti.notemate.Model.NotesEntity;
import com.shivshakti.notemate.R;
import com.shivshakti.notemate.ViewModel.NotesViewModel;
import com.shivshakti.notemate.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NotesViewModel notesViewModel;

    List<NotesEntity> allFilteredNotesList;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        // By default no filter selected
        binding.noFilterTV.setBackgroundResource(R.drawable.selected_filter_bg);
        binding.LowToHighFilterTV.setBackgroundResource(R.drawable.un_filter_bg);
        binding.HighToLowFilterTV.setBackgroundResource(R.drawable.un_filter_bg);

        // Observe executes whenever any changes is being made in list
        notesViewModel.getAllNotes.observe(this, notesEntities -> {

            if (notesEntities.isEmpty()){
                binding.noNoteTv.setVisibility(View.VISIBLE);
            }
            else {
                binding.noNoteTv.setVisibility(View.GONE);
            }

            // Setting StaggeredGridLayout which asymmetrical grid layout
            binding.allNotesRv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            adapter = new NotesAdapter(MainActivity.this, notesEntities);
            binding.allNotesRv.setAdapter(adapter);

            allFilteredNotesList = notesEntities;

        });

        binding.newNoteBtn.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this, InsertNoteActivity.class));
        });

        // Filter click listener
        filterClickListener();

    }

    public void filterClickListener(){

        binding.noFilterTV.setOnClickListener(v ->{

            binding.noFilterTV.setBackgroundResource(R.drawable.selected_filter_bg);
            binding.LowToHighFilterTV.setBackgroundResource(R.drawable.un_filter_bg);
            binding.HighToLowFilterTV.setBackgroundResource(R.drawable.un_filter_bg);

            // Observe executes whenever any changes is being made in list
            notesViewModel.getAllNotes.observe(this, notesEntities -> {

                if (notesEntities.isEmpty()){
                    binding.noNoteTv.setVisibility(View.VISIBLE);
                }
                else {
                    binding.noNoteTv.setVisibility(View.GONE);
                }

                // Setting StaggeredGridLayout which asymmetrical grid layout
                binding.allNotesRv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                adapter = new NotesAdapter(MainActivity.this, notesEntities);
                binding.allNotesRv.setAdapter(adapter);

                allFilteredNotesList = notesEntities;

            });

        });


        binding.LowToHighFilterTV.setOnClickListener(v ->{

            binding.LowToHighFilterTV.setBackgroundResource(R.drawable.selected_filter_bg);
            binding.noFilterTV.setBackgroundResource(R.drawable.un_filter_bg);
            binding.HighToLowFilterTV.setBackgroundResource(R.drawable.un_filter_bg);

            // Observe executes whenever any changes is being made in list
            notesViewModel.lowToHigh.observe(this, notesEntities -> {

                if (notesEntities.isEmpty()){
                    binding.noNoteTv.setVisibility(View.VISIBLE);
                }
                else {
                    binding.noNoteTv.setVisibility(View.GONE);
                }

                // Setting StaggeredGridLayout which asymmetrical grid layout
                binding.allNotesRv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                adapter = new NotesAdapter(MainActivity.this, notesEntities);
                binding.allNotesRv.setAdapter(adapter);

                allFilteredNotesList = notesEntities;

            });

        });


        binding.HighToLowFilterTV.setOnClickListener(v ->{

            binding.HighToLowFilterTV.setBackgroundResource(R.drawable.selected_filter_bg);
            binding.noFilterTV.setBackgroundResource(R.drawable.un_filter_bg);
            binding.LowToHighFilterTV.setBackgroundResource(R.drawable.un_filter_bg);

            // Observe executes whenever any changes is being made in list
            notesViewModel.highToLow.observe(this, notesEntities -> {

                if (notesEntities.isEmpty()){
                    binding.noNoteTv.setVisibility(View.VISIBLE);
                }
                else {
                    binding.noNoteTv.setVisibility(View.GONE);
                }

                // Setting StaggeredGridLayout which asymmetrical grid layout
                binding.allNotesRv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                adapter = new NotesAdapter(MainActivity.this, notesEntities);
                binding.allNotesRv.setAdapter(adapter);

                allFilteredNotesList = notesEntities;

            });

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_menu);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Find note...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredNotes(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void filteredNotes(String newText) {

        ArrayList<NotesEntity> filterNotes = new ArrayList<>();

        for (NotesEntity notes : this.allFilteredNotesList){
            if (notes.noteTitle.contains(newText) || notes.subTitle.contains(newText) || notes.notes.contains(newText)){
                filterNotes.add(notes);
            }

        }
        this.adapter.searchList(filterNotes);

    }
}