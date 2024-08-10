import {SubmitHandler, useForm} from "react-hook-form";
import {Note} from "@/types/notes";
import {useAddNote} from "@/hooks/useNotes";

const useNoteForm = (initialValues?: Omit<Note, "id">) => {
    const {register, handleSubmit, formState: {errors}} = useForm<Note>({
        defaultValues: initialValues
    });
    const addMutation = useAddNote();

    const onSubmit: SubmitHandler<Note> = (data) => addMutation.mutate(data);

    return {register, handleSubmit, onSubmit, errors};
}

export default useNoteForm;