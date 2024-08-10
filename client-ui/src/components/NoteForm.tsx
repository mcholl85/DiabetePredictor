import React, {FC} from "react";
import {Button, Label, Textarea, TextInput} from "flowbite-react";
import useNoteForm from "@/hooks/useNoteForm";

interface NoteFormProps {
    patId: number
    patient: string
    closeForm: () => void
}

const NoteForm: FC<NoteFormProps> = ({patId, patient, closeForm}) => {
    const {register, handleSubmit, onSubmit, errors} = useNoteForm({patId, patient, note: ''});

    const handleFormSubmit = async (data: any) => {
        try {
            await onSubmit(data);
            closeForm();
        } catch (error) {
            console.error("Form submission error:", error);
        }
    }

    return (
        <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-4">
            <Label htmlFor="note" value="Notes" hidden className="ml-1"/>
            <Textarea id="note" {...register('note', {required: 'Notes is required'})} color={errors.note ? 'failure' : 'info'} rows={4}/>
            {errors.note && <p className="text-red-600">{errors.note.message}</p>}
            <Button className="bg-cyan-800 text-white mt-1" type="submit">Ajouter</Button>
        </form>);
}

export default NoteForm;