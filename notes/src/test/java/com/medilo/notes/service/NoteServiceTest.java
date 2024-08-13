package com.medilo.notes.service;

import com.medilo.notes.model.Note;
import com.medilo.notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteService noteService;

    @Test
    void testGetNotesByPatientId() {
        Note note1 = new Note("1", 1, "John Doe", "Note 1");
        Note note2 = new Note("2", 1, "John Doe", "Note 2");

        when(noteRepository.findByPatId(1)).thenReturn(Arrays.asList(note1, note2));

        List<Note> notes = noteService.getNotesByPatientId(1);

        verify(noteRepository).findByPatId(1);

        assertThat(notes).hasSize(2);
        assertThat(notes).extracting(Note::getNote).contains("Note 1", "Note 2");
    }

    @Test
    void testCreateNote() {
        Note note = new Note("1", 1, "John Doe", "New Note");
        when(noteRepository.insert(any(Note.class))).thenReturn(note);

        Note createdNote = noteService.createNote(note);

        verify(noteRepository).insert(any(Note.class));

        assertThat(createdNote.getNote()).isEqualTo("New Note");
    }
}