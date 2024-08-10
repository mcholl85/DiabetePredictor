import {SubmitHandler, useForm} from 'react-hook-form';
import {Patient} from '@/types/patients';
import {useAddPatient, useUpdatePatient} from "@/hooks/usePatients";
import {useRouter} from "next/router";

const usePatientForm = (initialValues?: Patient) => {
    const {register, handleSubmit, formState: {errors}} = useForm<Patient>({
        defaultValues: initialValues,
    });
    const addMutation = useAddPatient();
    const updateMutation = useUpdatePatient();
    const router = useRouter();
    const id = initialValues?.id;

    const onSubmit: SubmitHandler<Patient> = (data: Patient) => {
        if (initialValues && id) {
            updateMutation.mutate({id, data}, {
                onSuccess: async () => {
                    await router.push("/patients");
                }
            });
        } else {
            addMutation.mutate(data, {
                onSuccess: async () => {
                    await router.push("/patients");
                }
            });
        }
    };

    return {register, handleSubmit, onSubmit, errors};
};

export default usePatientForm;