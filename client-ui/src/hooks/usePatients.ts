import {Patient} from '@/types/patients';
import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import {addPatient, fetchPatientById, fetchPatients, updatePatient} from "@/api/patient";

const usePatients = () => {
    return useQuery({
        queryKey: ['patients'],
        queryFn: fetchPatients,
    });
};

const usePatient = (id: number) => {
    return useQuery({
        queryKey: ['patient', id],
        queryFn: () => fetchPatientById(id),
        retry: 0
    })
}

const useAddPatient = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: addPatient,
        onSuccess: async () => {
            await queryClient.invalidateQueries({queryKey: ['patients']});
        }
    });
};

const useUpdatePatient = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({id, data}: {
            id: number,
            data: Patient
        }) => updatePatient(id, data),
        onSuccess: async (data, variables, context) => {
            await queryClient.invalidateQueries({queryKey: ['patients']});
            await queryClient.invalidateQueries({queryKey: ['patient', variables.id]});
            await queryClient.invalidateQueries({queryKey: ['risk', variables.id]});
        }
    })
}

export {usePatients, usePatient, useAddPatient, useUpdatePatient};