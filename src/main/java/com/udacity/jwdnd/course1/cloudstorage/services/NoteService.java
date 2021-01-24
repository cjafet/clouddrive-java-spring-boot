package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes() {
        return this.noteMapper.getNotes();
    }

    public List<Note> getNotesById(Integer noteId) {
        return this.noteMapper.getNotesById(noteId);
    }

    public int updateNote(Note note) {
        return this.noteMapper.updateNote(note);
    }

    public int createNote(Note note) {
        return this.noteMapper.insert(note);
    }

    public void deleteNote(Integer noteId) {
        this.noteMapper.deleteNote(noteId);
    }
}
