package com.medilo.notes.service;

import com.medilo.notes.model.Note;
import com.medilo.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getNotesByPatientId(Integer patId) {
        return noteRepository.findByPatId(patId);
    }

    public Note createNote(Note note) {
        return noteRepository.insert(note);
    }
}
