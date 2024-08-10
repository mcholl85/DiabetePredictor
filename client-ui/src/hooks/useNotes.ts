import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {addNote, fetchNoteByPatId} from "@/api/note";

const useNote = (patId: number) => {
    return useQuery({
        queryKey: ['note', patId],
        queryFn: () => fetchNoteByPatId(patId),
        retry: 0
    })
}

const useAddNote = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: addNote,
        onSuccess: async (data) => {
            await queryClient.invalidateQueries({queryKey: ['note', data.patId]});
            await queryClient.invalidateQueries({queryKey: ['risk', data.patId]});
        }
    })
}

export {useNote, useAddNote}