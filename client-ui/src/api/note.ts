import {Note} from "@/types/notes";
import {API_URL} from "@/constants/apiUrl";

const fetchNoteByPatId = async (id: number): Promise<Array<Note>> => {
    const response = await fetch(`${API_URL}/patients/${id}/notes`);

    if (!response.ok) {
        throw new Error('Patient not found');
    }
    return response.json();
}

const addNote = async (newNote: Note): Promise<Note> => {
    const response: Response = await fetch(`${API_URL}/patients/${newNote.patId}/notes`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newNote),
    })

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    return response.json();
};

export {fetchNoteByPatId, addNote}