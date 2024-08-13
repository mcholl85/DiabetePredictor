package com.medilo.notes.service;

package com.medilo.notes.service;

import com.medilo.notes.model.Note;
import com.medilo.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NoteServiceTest {
    private NoteRepository noteRepository;
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteRepository = Mockito.mock(NoteRepository.class);
        noteService = new NoteService(noteRepository);
    }

    @Test
    void testGetNotesByPatientId() {
        Note note1 = new Note("1", 1, "John Doe", "Note 1");
        Note note2 = new Note("2", 1, "John Doe", "Note 2");

        when(noteRepository.findByPatId(1)).thenReturn(Arrays.asList(note1, note2));

        List<Note> notes = noteService.getNotesByPatientId(1);
        assertThat(notes).hasSize(2);
        assertThat(notes).extracting(Note::getNote).contains("Note 1", "Note 2");
    }

    @Test
    void testCreateNote() {
        Note note = new Note("1", 1, "John Doe", "New Note");
        when(noteRepository.insert(any(Note.class))).thenReturn(note);

        Note createdNote = noteService.createNote(note);
        assertThat(createdNote.getNote()).isEqualTo("New Note");
    }
}