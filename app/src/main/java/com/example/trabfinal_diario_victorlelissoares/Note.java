package com.example.trabfinal_diario_victorlelissoares;

import java.io.Serializable;

public class Note implements Serializable {
    private int idNote;
    private int externIdUser;
    private String noteTitle;
    private String noteText;
    //e a música relacionada a nota


    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public int getExternIdUser() {
        return externIdUser;
    }

    public void setExternIdUser(int externIdUser) {
        this.externIdUser = externIdUser;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public String toString() {
        return this.getNoteTitle();
    }
}
