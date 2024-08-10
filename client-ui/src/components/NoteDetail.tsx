import {Card, Spinner, Textarea} from "flowbite-react";
import React, {useState} from "react";
import {useNote} from "@/hooks/useNotes";
import NoteForm from "@/components/NoteForm";

interface NoteDetailProps {
    patient: string
    patId: number
}

const NoteDetail: React.FC<NoteDetailProps> = ({patId, patient}) => {
    const {data, isLoading} = useNote(patId);
    const [formIsOpen, setFormIsOpen] = useState<boolean>(false);

    const closeForm = () => {
        setFormIsOpen(false);
    }

    return (<Card className="max-w-2xl mx-auto mt-1">
        <div className="flex justify-between items-center">
            <div className="font-bold text-xl text-cyan-800">Notes</div>
            <button className="text-cyan-800 hover:bg-gray-200 hover:text-black rounded-lg p-1.5"
                    type="button" onClick={() => setFormIsOpen(!formIsOpen)}>
                <svg className="h-6 w-6" width="24" height="24" viewBox="0 0 24 24" strokeWidth="2"
                     stroke="currentColor" fill="none" strokeLinecap="round" strokeLinejoin="round"
                >
                    <path stroke="none" d="M0 0h24v24H0z"/>
                    <rect x="4" y="4" width="16" height="16" rx="2"/>
                    <line x1="9" y1="12" x2="15" y2="12"/>
                    <line x1="12" y1="9" x2="12" y2="15"/>
                </svg>
            </button>
        </div>
        {isLoading ? <div className="text-center">
            <Spinner
                color="info"
                aria-label="Center-aligned spinner example"
                size="xl"
            />
        </div> : data?.map((note, count) => (
            <div key={note.id}>
                <Textarea id="note" value={note.note} readOnly rows={4} disabled/>
            </div>
        ))}
        {formIsOpen && <NoteForm patId={patId} patient={patient} closeForm={closeForm}/>}
    </Card>)
};

export default NoteDetail;