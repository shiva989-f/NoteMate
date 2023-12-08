package com.shivshakti.notemate.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shivshakti.notemate.Activities.UpdateNoteActivity;
import com.shivshakti.notemate.Model.NotesEntity;
import com.shivshakti.notemate.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    Context context;
    List<NotesEntity> notesEntityList;
    List<NotesEntity> allFindMatches;

    public NotesAdapter(Context context, List<NotesEntity> notesEntityList){

        this.context = context;
        this.notesEntityList = notesEntityList;
        this.allFindMatches = new ArrayList<>(notesEntityList);

    }

    public void searchList(List<NotesEntity> findMatchesNotes){

        this.notesEntityList = findMatchesNotes;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        NotesEntity note = notesEntityList.get(position);

        holder.titleTv.setText(note.noteTitle);
        holder.subtitleTv.setText(note.subTitle);
        holder.noteTv.setText(note.notes);
        holder.dateTv.setText(note.noteDate);

        // Checking if noteTitle, subTitle and note is empty or not
        if (note.noteTitle.isEmpty()&& note.subTitle.isEmpty()){
            holder.titleTv.setVisibility(View.GONE);
            holder.subtitleTv.setVisibility(View.GONE);
        } else if (note.subTitle.isEmpty() && note.notes.isEmpty()) {
            holder.subtitleTv.setVisibility(View.GONE);
            holder.noteTv.setVisibility(View.GONE);
        } else if (note.notes.isEmpty() && note.noteTitle.isEmpty()) {
            holder.noteTv.setVisibility(View.GONE);
            holder.titleTv.setVisibility(View.GONE);
        }
        else if (note.noteTitle.isEmpty()) {
            holder.titleTv.setVisibility(View.GONE);
        }
        else if (note.subTitle.isEmpty()) {
            holder.subtitleTv.setVisibility(View.GONE);
        }
        else if (note.notes.isEmpty()) {
            holder.noteTv.setVisibility(View.GONE);
        }

        // Setting priorityView background color according to priority
        switch (note.notesPriority){
            case "1":
                holder.priorityView.setBackgroundResource(R.color.green);
                break;
            case "2":
                holder.priorityView.setBackgroundResource(R.color.yellow);
                break;
            case "3":
                holder.priorityView.setBackgroundResource(R.color.tomato);
                break;
        }

        holder.itemView.setOnClickListener(v ->{
            Intent iUpdateActivity = new Intent(context, UpdateNoteActivity.class);
            iUpdateActivity.putExtra("NOTE_ID", note.id);
            iUpdateActivity.putExtra("NOTE_TITLE", note.noteTitle);
            iUpdateActivity.putExtra("NOTE_SUBTITLE", note.subTitle);
            iUpdateActivity.putExtra("NOTE_PRIORITY", note.notesPriority);
            iUpdateActivity.putExtra("NOTE", note.notes);
            context.startActivity(iUpdateActivity);
        });

    }
    @Override
    public int getItemCount() {
        return notesEntityList.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView titleTv, subtitleTv, noteTv, dateTv;
        View priorityView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.rv_title);
            subtitleTv = itemView.findViewById(R.id.rv_subtitle);
            noteTv = itemView.findViewById(R.id.rv_note);
            dateTv = itemView.findViewById(R.id.rv_date);

            priorityView = itemView.findViewById(R.id.rv_priority);

        }
    }
}
