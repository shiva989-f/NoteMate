package com.shivshakti.notemate.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NotesDB")
public class NotesEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "notes_title")
    public String noteTitle;
    @ColumnInfo(name = "notes_subTitle")
    public String subTitle;
    @ColumnInfo(name = "notes")
    public String notes;
    @ColumnInfo(name = "notes_date")
    public String noteDate;
    @ColumnInfo(name = "notes_priority")
    public String notesPriority;

}
