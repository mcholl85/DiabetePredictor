package com.medilo.notes.controller;

import com.medilo.notes.model.Note;
import com.medilo.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/patId/{patientId}")
    public List<Note> getNotesByPatientId(@PathVariable Integer patientId) {
        return noteService.getNotesByPatientId(patientId);
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }
}
